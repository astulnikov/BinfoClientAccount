package by.binfo.BusinessInform;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private final static String DB_NAME = "binfo";
	private final static String FIELD_NAME = "name";
	private final static String FIELD_ID = "id";
	private final static String FIELD_ZIP = "zip";
	private final static String FIELD_REGION = "region";
	private final static String FIELD_DISTRICT = "district";
	private final static String FIELD_CITY = "city";
	private final static String FIELD_STREET = "street";
	private final static String FIELD_BUILDING = "building_no";
	private final static String FIELD_PHONE = "phone";
	private final static String FIELD_PIC = "pic";
	private final static String FIELD_RUBRIC = "rubric";
	private final static String FIELD_DIRECTION = "direction";
	private final static String FIELD_EMAIL = "email";
	private final static String FIELD_SITE = "site";
	private final static String FIELD_KEYWORDS = "keywords";
	private final static String FIELD_VIP = "vip";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 5);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(Utils.LOG_TAG, "---onCreate Database---");
		createTableFood(db);
		createTableStroy(db);
		createTableExport(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Utils.TAB_NAME_FOOD);
		db.execSQL("DROP TABLE IF EXISTS " + Utils.TAB_NAME_STR);
		db.execSQL("DROP TABLE IF EXISTS " + Utils.TAB_NAME_EXPORT);
		onCreate(db);
	}

	public void dropTable(String tabName) {
		SQLiteDatabase rDb = getReadableDatabase();
		rDb.execSQL("DROP TABLE IF EXISTS " + tabName);
		if (tabName == Utils.TAB_NAME_FOOD) {
			createTableFood(rDb);
		} else if (tabName == Utils.TAB_NAME_STR) {
			createTableStroy(rDb);
		} else {
			createTableExport(rDb);
		}
		rDb.close();
	}

	public void createTableFood(SQLiteDatabase db) {
		db.execSQL("create table " + Utils.TAB_NAME_FOOD + " (" + FIELD_ID
				+ " text primary key, " + FIELD_NAME + " text, " + FIELD_ZIP
				+ " integer, " + FIELD_REGION + " text, " + FIELD_DISTRICT
				+ " text, " + FIELD_CITY + " text, " + FIELD_STREET + " text, "
				+ FIELD_BUILDING + " text, " + FIELD_PHONE + " text, "
				+ FIELD_PIC + " text, " + FIELD_RUBRIC + " text, "
				+ FIELD_DIRECTION + " text, " + FIELD_EMAIL + " text, "
				+ FIELD_SITE + " text, " + FIELD_KEYWORDS + " text, " + FIELD_VIP
				+ " integer" + ");");
		Log.d(Utils.LOG_TAG, "---Table Created!---");
	}

	public void createTableStroy(SQLiteDatabase db) {

	}

	public void createTableExport(SQLiteDatabase db) {

	}

	public int addCompany(Company company) {
		Log.d(Utils.LOG_TAG, "--- Insert in mytable: ---");
		// подготовим данные для вставки в виде пар: наименование столбца -
		// значение

		SQLiteDatabase rDb = getReadableDatabase();
		final ContentValues cv = new ContentValues();
		cv.put(FIELD_ID, company.getId());
		cv.put(FIELD_NAME, company.getName());
		cv.put(FIELD_ZIP, company.getZip());
		cv.put(FIELD_REGION, company.getRegion());
		cv.put(FIELD_DISTRICT, company.getDistrict());
		cv.put(FIELD_CITY, company.getCity());
		cv.put(FIELD_STREET, company.getStreet());
		cv.put(FIELD_BUILDING, company.getBuilding_no());
		cv.put(FIELD_PHONE, company.getPhone());
		cv.put(FIELD_PIC, company.getPicString());
		cv.put(FIELD_RUBRIC, company.getRubric());
		cv.put(FIELD_DIRECTION, company.getDirection());
		cv.put(FIELD_EMAIL, company.geteMail());
		cv.put(FIELD_SITE, company.getSite());
		cv.put(FIELD_KEYWORDS, company.getKeyWords());
		cv.put(FIELD_VIP, company.getVip());
		Log.d(Utils.LOG_TAG, cv.toString());
		// вставляем запись и получаем ее ID
		long rowID = rDb.insert(Utils.tabName, null, cv);
		rDb.close();
		return (int) rowID;
	}

	public Company getCompany(String id) {
		Company company = null;
		SQLiteDatabase rDb = getReadableDatabase();
		String sql = "SELECT * FROM " + Utils.tabName + " WHERE " + FIELD_ID
				+ " = '" + id + "'";
		Log.d(Utils.LOG_TAG, sql);
		Cursor c = rDb.rawQuery(sql, null);
		if (c.moveToFirst()) {
			// определяем номера столбцов по имени в выборке
			int idColIndex = c.getColumnIndex(FIELD_ID);
			int nameColIndex = c.getColumnIndex(FIELD_NAME);
			int zipColIndex = c.getColumnIndex(FIELD_ZIP);
			int regionColIndex = c.getColumnIndex(FIELD_REGION);
			int districtColIndex = c.getColumnIndex(FIELD_DISTRICT);
			int cityColIndex = c.getColumnIndex(FIELD_CITY);
			int streetColIndex = c.getColumnIndex(FIELD_STREET);
			int buildingColIndex = c.getColumnIndex(FIELD_BUILDING);
			int phoneColIndex = c.getColumnIndex(FIELD_PHONE);
			int directionColIndex = c.getColumnIndex(FIELD_DIRECTION);
			int picsColIndex = c.getColumnIndex(FIELD_PIC);
			int emailColIndex = c.getColumnIndex(FIELD_EMAIL);
			int siteColIndex = c.getColumnIndex(FIELD_SITE);
			int keyWordsColIndex = c.getColumnIndex(FIELD_KEYWORDS);
			int rubricColIndex = c.getColumnIndex(FIELD_RUBRIC);
			company = new Company(c.getString(idColIndex),
					c.getString(nameColIndex), c.getString(regionColIndex),
					c.getString(districtColIndex), c.getString(cityColIndex),
					c.getString(streetColIndex), c.getInt(zipColIndex),
					c.getString(buildingColIndex), c.getString(phoneColIndex),
					c.getString(emailColIndex), c.getString(siteColIndex),
					c.getString(keyWordsColIndex), c.getString(rubricColIndex),
					c.getString(picsColIndex), c.getString(directionColIndex));
		} else
			Log.d(Utils.LOG_TAG, "0 rows");
		c.close();
		rDb.close();
		return company;
	}

	public ArrayList<Company> getCompaniesRubric(String rubric) {
		ArrayList<Company> companyList = new ArrayList<Company>();

		Log.d(Utils.LOG_TAG, "--- Rows in mytable: ---");
		SQLiteDatabase rDb = getReadableDatabase();
		// делаем запрос всех данных из таблицы mytable, получаем Cursor
		String sql = "SELECT * FROM " + Utils.tabName + " WHERE "
				+ FIELD_RUBRIC + " LIKE '%" + rubric + "%'";
		Log.d(Utils.LOG_TAG, sql);
		Cursor c = rDb.rawQuery(sql, null);
		// ставим позицию курсора на первую строку выборки
		// если в выборке нет строк, вернется false
		if (c.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int idColIndex = c.getColumnIndex(FIELD_ID);
			int nameColIndex = c.getColumnIndex(FIELD_NAME);
			int zipColIndex = c.getColumnIndex(FIELD_ZIP);
			int regionColIndex = c.getColumnIndex(FIELD_REGION);
			int cityColIndex = c.getColumnIndex(FIELD_CITY);
			int rubricColIndex = c.getColumnIndex(FIELD_RUBRIC);
			int picsColIndex = c.getColumnIndex(FIELD_PIC);
			int vipColIndex = c.getColumnIndex(FIELD_VIP);

			do {
				// получаем значения по номерам столбцов и пишем все в лог
				Company company = new Company(c.getString(idColIndex),
						c.getString(nameColIndex), c.getInt(zipColIndex),
						c.getString(regionColIndex), c.getString(cityColIndex),
						c.getString(rubricColIndex), c.getString(picsColIndex), c.getInt(vipColIndex));
				companyList.add(company);
				// переход на следующую строку
				// а если следующей нет (текущая - последняя), то false -
				// выходим из цикла
			} while (c.moveToNext());
		} else
			Log.d(Utils.LOG_TAG, "0 rows");
		c.close();
		rDb.close();
		return companyList;
	}

	public ArrayList<Company> getVip() {
		ArrayList<Company> companyList = new ArrayList<Company>();

		Log.d(Utils.LOG_TAG, "--- Rows in mytable: ---");
		SQLiteDatabase rDb = getReadableDatabase();
		// делаем запрос всех данных из таблицы mytable, получаем Cursor
		String sql = "SELECT * FROM " + Utils.tabName + " WHERE " + FIELD_VIP
				+ " = '1'";
		Log.d(Utils.LOG_TAG, sql);
		Cursor c = rDb.rawQuery(sql, null);
		Log.d(Utils.LOG_TAG, "Rows: " + c.getCount());
		// ставим позицию курсора на первую строку выборки
		// если в выборке нет строк, вернется false
		if (c.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int idColIndex = c.getColumnIndex(FIELD_ID);
			int nameColIndex = c.getColumnIndex(FIELD_NAME);
			int zipColIndex = c.getColumnIndex(FIELD_ZIP);
			int regionColIndex = c.getColumnIndex(FIELD_REGION);
			int cityColIndex = c.getColumnIndex(FIELD_CITY);
			int rubricColIndex = c.getColumnIndex(FIELD_RUBRIC);
			int picsColIndex = c.getColumnIndex(FIELD_PIC);
			int vipColIndex = c.getColumnIndex(FIELD_VIP);

			do {
				// получаем значения по номерам столбцов и пишем все в лог
				Company company = new Company(c.getString(idColIndex),
						c.getString(nameColIndex), c.getInt(zipColIndex),
						c.getString(regionColIndex), c.getString(cityColIndex),
						c.getString(rubricColIndex), c.getString(picsColIndex), c.getInt(vipColIndex));
				companyList.add(company);
				// переход на следующую строку
				// а если следующей нет (текущая - последняя), то false -
				// выходим из цикла
			} while (c.moveToNext());
		} else {
			Log.d(Utils.LOG_TAG, "0 rows");
		}
		c.close();
		rDb.close();
		return companyList;
	}

	public ArrayList<Company> getCompaniesAlphabet(String firstLetter) {
		ArrayList<Company> companyList = new ArrayList<Company>();

		Log.d(Utils.LOG_TAG, "--- Rows in mytable: ---");
		SQLiteDatabase rDb = getReadableDatabase();
		// делаем запрос всех данных из таблицы mytable, получаем Cursor
		String sql = "SELECT * FROM " + Utils.tabName + " WHERE " + FIELD_NAME
				+ " LIKE '" + firstLetter + "%'";
		Log.d(Utils.LOG_TAG, sql);
		Cursor c = rDb.rawQuery(sql, null);
		// ставим позицию курсора на первую строку выборки
		// если в выборке нет строк, вернется false
		if (c.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int idColIndex = c.getColumnIndex(FIELD_ID);
			int nameColIndex = c.getColumnIndex(FIELD_NAME);
			int zipColIndex = c.getColumnIndex(FIELD_ZIP);
			int regionColIndex = c.getColumnIndex(FIELD_REGION);
			int cityColIndex = c.getColumnIndex(FIELD_CITY);
			int rubricColIndex = c.getColumnIndex(FIELD_RUBRIC);
			int picsColIndex = c.getColumnIndex(FIELD_PIC);
			int vipColIndex = c.getColumnIndex(FIELD_VIP);

			do {
				// получаем значения по номерам столбцов и пишем все в лог
				Company company = new Company(c.getString(idColIndex),
						c.getString(nameColIndex), c.getInt(zipColIndex),
						c.getString(regionColIndex), c.getString(cityColIndex),
						c.getString(rubricColIndex), c.getString(picsColIndex), c.getInt(vipColIndex));
				companyList.add(company);
				// переход на следующую строку
				// а если следующей нет (текущая - последняя), то false -
				// выходим из цикла
			} while (c.moveToNext());
		} else
			Log.d(Utils.LOG_TAG, "0 rows");
		c.close();
		rDb.close();
		return companyList;
	}

	public ArrayList<Company> getCompaniesRegion(String region) {
		ArrayList<Company> companyList = new ArrayList<Company>();

		Log.d(Utils.LOG_TAG, "--- Rows in mytable: ---");
		SQLiteDatabase rDb = getReadableDatabase();
		// делаем запрос всех данных из таблицы mytable, получаем Cursor
		String sql = "SELECT * FROM " + Utils.tabName + " WHERE "
				+ FIELD_REGION + " = '" + region + "'";
		Log.d(Utils.LOG_TAG, sql);
		Cursor c = rDb.rawQuery(sql, null);
		// ставим позицию курсора на первую строку выборки
		// если в выборке нет строк, вернется false
		if (c.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int idColIndex = c.getColumnIndex(FIELD_ID);
			int nameColIndex = c.getColumnIndex(FIELD_NAME);
			int zipColIndex = c.getColumnIndex(FIELD_ZIP);
			int regionColIndex = c.getColumnIndex(FIELD_REGION);
			int cityColIndex = c.getColumnIndex(FIELD_CITY);
			int rubricColIndex = c.getColumnIndex(FIELD_RUBRIC);
			int picsColIndex = c.getColumnIndex(FIELD_PIC);
			int vipColIndex = c.getColumnIndex(FIELD_VIP);

			do {
				// получаем значения по номерам столбцов и пишем все в лог
				Company company = new Company(c.getString(idColIndex),
						c.getString(nameColIndex), c.getInt(zipColIndex),
						c.getString(regionColIndex), c.getString(cityColIndex),
						c.getString(rubricColIndex), c.getString(picsColIndex), c.getInt(vipColIndex));
				companyList.add(company);
				// переход на следующую строку
				// а если следующей нет (текущая - последняя), то false -
				// выходим из цикла
			} while (c.moveToNext());
		} else
			Log.d(Utils.LOG_TAG, "0 rows");
		c.close();
		rDb.close();
		return companyList;
	}

	public ArrayList<Company> searchCompanies(String name) {
		ArrayList<Company> companyList = new ArrayList<Company>();

		Log.d(Utils.LOG_TAG, "--- Rows in mytable: ---");
		SQLiteDatabase rDb = getReadableDatabase();
		// делаем запрос всех данных из таблицы mytable, получаем Cursor
		String sql = "SELECT * FROM " + Utils.tabName + " WHERE " + FIELD_NAME
				+ " LIKE '%" + name + "%'";
		Log.d(Utils.LOG_TAG, sql);
		Cursor c = rDb.rawQuery(sql, null);
		// ставим позицию курсора на первую строку выборки
		// если в выборке нет строк, вернется false
		if (c.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int idColIndex = c.getColumnIndex(FIELD_ID);
			int nameColIndex = c.getColumnIndex(FIELD_NAME);
			int zipColIndex = c.getColumnIndex(FIELD_ZIP);
			int regionColIndex = c.getColumnIndex(FIELD_REGION);
			int cityColIndex = c.getColumnIndex(FIELD_CITY);
			int rubricColIndex = c.getColumnIndex(FIELD_RUBRIC);
			int picsColIndex = c.getColumnIndex(FIELD_PIC);
			int vipColIndex = c.getColumnIndex(FIELD_VIP);

			do {
				// получаем значения по номерам столбцов и пишем все в лог
				Company company = new Company(c.getString(idColIndex),
						c.getString(nameColIndex), c.getInt(zipColIndex),
						c.getString(regionColIndex), c.getString(cityColIndex),
						c.getString(rubricColIndex), c.getString(picsColIndex), c.getInt(vipColIndex));
				companyList.add(company);
				// переход на следующую строку
				// а если следующей нет (текущая - последняя), то false -
				// выходим из цикла
			} while (c.moveToNext());
		} else
			Log.d(Utils.LOG_TAG, "0 rows");
		c.close();
		rDb.close();
		return companyList;
	}

}
