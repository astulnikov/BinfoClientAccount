package by.binfo.BusinessInform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.Environment;
import android.util.Log;

public class FileDownloader {
	private static String serv, form = ".gif";

	public FileDownloader(Context self) {
	}

	public static String dowmloadPic(String picName, int picType) {
		if (Utils.tabName.equals(Utils.TAB_NAME_FOOD)) {
			switch (picType) {
			case Utils.PIC_TYPE_LOGO:
				serv = "http://b-info.by/" + Utils.FOOD_LOGOS;
				break;
			case Utils.PIC_TYPE_VIP:
				serv = "http://b-info.by/" + Utils.FOOD_VIP;
				break;
			case Utils.PIC_TYPE_MODULE:
				serv = "http://b-info.by/" + Utils.FOOD_MODULES;
				break;
			}
		} else if(Utils.tabName.equals(Utils.TAB_NAME_EXPORT)){
			switch (picType) {
			case Utils.PIC_TYPE_LOGO:
				serv = "http://b-info.by/" + Utils.EXPORT_LOGOS;
				break;
			case Utils.PIC_TYPE_VIP:
				serv = "http://b-info.by/" + Utils.EXPORT_VIP;
				break;
			case Utils.PIC_TYPE_MODULE:
				serv = "http://b-info.by/" + Utils.EXPORT_MODULES;
				break;
			}
		} else{
			switch (picType) {
			case Utils.PIC_TYPE_LOGO:
				serv = "http://b-info.by/" + Utils.CONSTR_LOGOS;
				break;
			case Utils.PIC_TYPE_VIP:
				serv = "http://b-info.by/" + Utils.CONSTR_VIP;
				break;
			case Utils.PIC_TYPE_MODULE:
				serv = "http://b-info.by/" + Utils.CONSTR_MODULES;
				break;
			}
		}
		Log.d(Utils.LOG_TAG, "url:" + serv + picName + form);
		Bitmap poster = downloadBitmap(serv + picName + form);
		if (poster != null) {
			Log.d(Utils.LOG_TAG, "Image downloaded! ");
			OutputStream outStream = null;
			// получаем путь к SD
			File sdPath = Environment.getExternalStorageDirectory();
			// добавляем свой каталог к пути
			sdPath = new File(sdPath.getAbsolutePath() + "/" + Utils.DIR + "/"
					+ Utils.tabName);
			// создаем каталог
			sdPath.mkdirs();
			// формируем объект File, который содержит путь
			// к файлу
			picName = "" + picType + new Date().getTime();
			File sdFile = new File(sdPath, picName + ".JPG");
			try {
				outStream = new FileOutputStream(sdFile);
				poster.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
				outStream.flush();
				outStream.close();
				Log.d(Utils.LOG_TAG,
						"Файл записан на SD: " + sdFile.getAbsolutePath());
			} catch (Exception e) {
			}
		}
		return picName;
	}

	public static Bitmap downloadBitmap(String url) {
		Log.d(Utils.LOG_TAG, "In download " + url);
		final AndroidHttpClient client = AndroidHttpClient
				.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			Log.d(Utils.LOG_TAG, "execute");
			if (statusCode != HttpStatus.SC_OK) {
				Log.e(Utils.LOG_TAG, "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					final Bitmap bitmap = BitmapFactory
							.decodeStream(inputStream);
					return bitmap;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// Could provide a more explicit error message for IOException or
			// IllegalStateException
			e.setStackTrace(null);
			getRequest.abort();
			Log.e(Utils.LOG_TAG, "Error while retrieving bitmap from " + url);
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return null;
	}

	public static void deleteFiles() {
		File sdPath = Environment.getExternalStorageDirectory();
		// добавляем свой каталог к пути
		sdPath = new File(sdPath.getAbsolutePath() + "/" + Utils.DIR + "/"
				+ Utils.tabName);
		if (sdPath.exists()) {
			File[] listFiles = sdPath.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				Log.d(Utils.LOG_TAG, "Deleted: " + listFiles[i].getName());
				listFiles[i].delete();
			}
			sdPath.delete();
		}
	}

}
