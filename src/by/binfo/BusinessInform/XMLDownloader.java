package by.binfo.BusinessInform;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xml.sax.Attributes;

import android.content.Context;
import android.content.SharedPreferences;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.sax.TextElementListener;
import android.util.Log;
import android.util.Xml;

public class XMLDownloader {
	private Company companyItem;
	private DBHelper dbHelp;
	private int picType;
	private String rubric = "", pic = "", direction = "";
	Boolean isVip;
	SharedPreferences binfoSP;

	public XMLDownloader(Context self) {
		dbHelp = new DBHelper(self);
	}

	public Boolean getXMLStream(String xmlUrl) {
		BufferedReader reader = null;
		HttpURLConnection connection = null;
		try {
			URL url = new URL(xmlUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream bInputStream = new BufferedInputStream(
						connection.getInputStream());
				reader = new BufferedReader(new InputStreamReader(bInputStream));
				if (!saxParse(reader)) {
					return false;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		Log.d(Utils.LOG_TAG, "XML Stream has been readed!");
		return true;
	}

	/**
	 * Для больших XML чтение из потока
	 * 
	 * @param in
	 * @return
	 */
	public boolean saxParse(BufferedReader in) {
		Utils.downloading = true;
		RootElement root = new RootElement("companies");
		Element company = root.getChild("company");
		Element rubrica = company.getChild("rubrika");
		Element code = rubrica.getChild("product").getChild("code");
		Element picName = rubrica.getChild("product").getChild("picture_name");
		Element activity = rubrica.getChild("activity");
		Element vip = company.getChild("vip");
		Element codeVip = vip.getChild("product").getChild("code");
		Element picNameVip = vip.getChild("product").getChild("picture_name");
		root.setEndElementListener(new EndElementListener() {
			public void end() {
				Log.d(Utils.LOG_TAG, "Downloader: Parsing is cancel");
				Utils.downloading = false;
			}
		});

		company.setStartElementListener(new StartElementListener() {

			public void start(Attributes attributes) {
				if (Utils.downloading) {
					Log.d(Utils.LOG_TAG, "Downloading: " + Utils.downloading);
					companyItem = new Company();
				} else {
					return;
				}
			}
		});

		company.setEndElementListener(new EndElementListener() {

			public void end() {
				if (Utils.downloading) {
					int rowID = dbHelp.addCompany(companyItem);
					Log.d(Utils.LOG_TAG, "row inserted, ID = " + rowID);
					if (rowID % 10 == 0) {
						MainActivity.handler.sendEmptyMessage(rowID);
					}
				}
			}
		});
		company.getChild("ID").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						companyItem.setId(body);
					}
				});
		company.getChild("name").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						companyItem.setName(body);
					}
				});
		company.getChild("region").setTextElementListener(
				new TextElementListener() {

					public void end(String body) {
					}

					public void start(Attributes attributes) {
						companyItem.setRegion(attributes.getValue(0));
					}
				});
		company.getChild("zip").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						if (body.replaceAll(" ", "") != "") {
							companyItem.setZip(Integer.parseInt(body
									.replaceAll(" ", "")));
						}
					}
				});
		company.getChild("district").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						companyItem.setDistrict(body);
					}
				});
		company.getChild("settlement").setTextElementListener(
				new TextElementListener() {
					String type;

					public void end(String body) {
						companyItem.setCity(type + body);
					}

					public void start(Attributes attributes) {
						type = attributes.getValue(0);
					}
				});
		company.getChild("street").setTextElementListener(
				new TextElementListener() {
					String type;

					public void end(String body) {
						companyItem.setStreet(type + body);
					}

					public void start(Attributes attributes) {
						type = attributes.getValue(0);
					}
				});
		company.getChild("building_no").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						companyItem.setBuilding_no(body);
					}
				});

		company.getChild("phone").setTextElementListener(
				new TextElementListener() {
					String code;

					public void end(String body) {
						companyItem.setPhone(code + body);
					}

					public void start(Attributes attributes) {
						code = "+375" + attributes.getValue(1);
					}
				});

		company.getChild("email").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						companyItem.seteMail(body);
					}
				});

		company.getChild("www").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						companyItem.setSite(body);
					}
				});

		company.getChild("keywords").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						companyItem.setKeyWords(body);
					}
				});

		rubrica.setStartElementListener(new StartElementListener() {

			public void start(Attributes attributes) {
				rubric = rubric + attributes.getValue(0);
			}
		});

		code.setEndTextElementListener(new EndTextElementListener() {

			public void end(String body) {
				if (body.startsWith("L")) {
					picType = Utils.PIC_TYPE_LOGO;
				} else {
					picType = Utils.PIC_TYPE_MODULE;
				}
			}
		});

		picName.setEndTextElementListener(new EndTextElementListener() {

			public void end(String body) {
				Log.d(Utils.LOG_TAG, "Attribute : " + picType + ": " + body);
				body.replaceAll(" ", "");
				pic = FileDownloader.dowmloadPic(body, picType);
				picType = 0;
			}
		});

		activity.getChild("direction").setEndTextElementListener(
				new EndTextElementListener() {

					public void end(String body) {
						direction = direction + body + " ";
					}
				});

		codeVip.setEndTextElementListener(new EndTextElementListener() {
			public void end(String body) {
				isVip = true;
				companyItem.setVip(1);
				picType = Utils.PIC_TYPE_VIP;
			}
		});

		picNameVip.setEndTextElementListener(new EndTextElementListener() {

			public void end(String body) {
				if (isVip) {
					Log.d(Utils.LOG_TAG, "Attribute VIP: " + body);
					body.replaceAll(" ", "");
					pic = FileDownloader.dowmloadPic(body, picType);
					companyItem.setPic(pic);
					picType = 0;
					isVip = false;
				}
			}
		});

		rubrica.setEndElementListener(new EndElementListener() {

			public void end() {
				companyItem.setRubric(rubric);
				companyItem.setPic(pic);
				companyItem.setDirection(direction);
				rubric = pic = direction = "";
			}
		});

		try {
			Xml.parse(in, root.getContentHandler());
		} catch (Exception e) {
			Utils.onError();
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
