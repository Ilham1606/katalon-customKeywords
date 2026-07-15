package id.co.fif.finform
import utils.BasePage
import utils.HelpersUtility

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.SelectorMethod


public class FinformKeywords extends BasePage{
	

	private String baseUrl

	public FinformKeywords(String url) {
		this.baseUrl = url
	}

	private static final ThreadLocal<Map<String, String>> sessionData = new ThreadLocal<Map<String, String>>() {
		@Override
		protected Map<String, String> initialValue() {
			return new HashMap<String, String>()
		}
	}

	public static void setData(String key, String value) {
		sessionData.get().put(key, value)
		KeywordUtil.logInfo("[DATA BRIDGE] Saved -> ${key}: ${value}")
	}

	@Keyword
	public static String getData(String key) {
		String value = sessionData.get().get(key)
		KeywordUtil.logInfo("[DATA BRIDGE] Retrieved -> ${key}: ${value}")
		return value != null ? value : ""
	}

	@Keyword
	public static void clearAllData() {
		sessionData.get().clear()
		sessionData.remove()
		KeywordUtil.logInfo("[DATA BRIDGE] Memory cleared.")
	}


	@Keyword
	public FinformKeywords loginFinform(String username, String password) {

		WebUI.navigateToUrl(this.baseUrl)

		TestObject fieldUsernameObj = findTestObject('Object Repository/18_finform/01_loginPage/01_fieldUsername')
		TestObject fieldPasswordObj = findTestObject('Object Repository/18_finform/01_loginPage/02_fieldPassword')
		TestObject btnSignInObj = findTestObject('Object Repository/18_finform/01_loginPage/03_btnSignIn')
		
		waitForAndSetText(fieldUsernameObj, username, "FIELD USERNAME")
		waitForAndSetText(fieldPasswordObj, password, "FELD PASSWORD")
		waitForAndClick(btnSignInObj, "BUTTON SIGN IN")
		
		WebUI.waitForPageLoad(30)

		return this
	}

	@Keyword
	public FinformKeywords logoutFinform() {
		boolean isNavbar = WebUI.verifyElementPresent(findTestObject('Object Repository/18_finform/01_navUser'), 10)
		//		boolean isBtnLogout = WebUI.verifyElementPresent(findTestObject('Object Repository/18_finform/02_btnLogout'), 10)

		if (!isNavbar) { // || !isBtnLogout
			KeywordUtil.markFailed("[ERROR] Admin icon or Logout button is not found.")
			throw new Exception("[ERROR] Logout failed.")
		}
		WebUI.click(findTestObject('Object Repository/18_finform/01_navUser'))
		WebUI.click(findTestObject('Object Repository/18_finform/02_btnLogout'))
		WebUI.click(findTestObject('Object Repository/18_finform/03_alertConfirm_logout'))
		WebUI.click(findTestObject('Object Repository/18_finform/04_alert_successLogout'))

		return this
	}


	@Keyword
	public String getDealerID() {

		TestObject dealerIDObj = findTestObject('Object Repository/18_finform/03_registerPage/14_fieldDealerID_DF')

		boolean isDealerID = WebUI.verifyElementPresent(dealerIDObj, 10, FailureHandling.OPTIONAL)

		String value = WebUI.getText(dealerIDObj)

		if (value == null || value.trim().isEmpty()) {
			KeywordUtil.markWarning("[WARN] Element dealerID found, but return null.")
		} else {
			KeywordUtil.logInfo("[INFO] Success get DealerID: " + value)
		}

		String trimmedValue =  value.trim()

		return trimmedValue
	}


	@Keyword
	public FinformKeywords goToMenuLoanTransaction() {
		TestObject menuLoanTransaction = findTestObject('Object Repository/18_finform/05_loanPage/01_menu_loanTransaction')

		boolean isMenuLoanTransaction = WebUI.verifyElementPresent(menuLoanTransaction, 10)

		if(!isMenuLoanTransaction) {
			KeywordUtil.markFailed("[ERROR] Failed click menu Loan Transaction.")
			throw new Exception("[ERROR] Step stop: Menu Loan Transaction not found.")
		}

		WebUI.click(menuLoanTransaction)

		return this
	}

	@Keyword
	public FinformKeywords goToSubmenuLoan() {

		goToMenuLoanTransaction()

		TestObject subMenuLoan = findTestObject('Object Repository/18_finform/05_loanPage/02_submenu_loan')

		boolean isSubmenuLoan = WebUI.verifyElementPresent(subMenuLoan, 10)

		if(!isSubmenuLoan) {
			KeywordUtil.markFailed("[ERROR] Failed click submenu Loan.")
			throw new Exception("[ERROR] Step stop: Submenu Loan not found.")
		}

		WebUI.click(subMenuLoan)

		return this
	}

	@Keyword
	public FinformKeywords goToSubmenuLoanHistory() {

		goToMenuLoanTransaction()

		TestObject subMenuLoanHistory = findTestObject('Object Repository/18_finform/05_loanPage/04_submenu_loanHistory')

		boolean isSubmenuLoan = WebUI.verifyElementPresent(subMenuLoanHistory, 10)

		if(!isSubmenuLoan) {
			KeywordUtil.markFailed("[ERROR] Failed click submenu Loan History.")
			throw new Exception("[ERROR] Step stop: Submenu Loan History not found.")
		}

		WebUI.click(subMenuLoanHistory)

		return this
	}

	@Keyword
	public FinformKeywords searchLoansByInvoiceNo(String invoiceNo) {
		TestObject fieldInvoiceNo = findTestObject('Object Repository/18_finform/05_loanPage/03_field_invoiceNumber')
		TestObject btnSearch = findTestObject('Object Repository/18_finform/buttonSearch')

		boolean isFieldInvoiceNo = WebUI.verifyElementPresent(fieldInvoiceNo, 10)

		if(!isFieldInvoiceNo) {
			KeywordUtil.markFailed("[ERROR] Failed input Invoice Number.")
			throw new Exception("[ERROR] Step stop: Field Invoice Number not found.")
		}

		WebUI.setText(fieldInvoiceNo, invoiceNo)
		WebUI.click(btnSearch)

		return this
	}

	@Keyword
	public FinformKeywords goToLoansPaymentSummary() {

		TestObject titlePaymentSummary = HelpersUtility.createTestObjectWithID(
				"xpath",
				"//div//p[contains(normalize-space(), 'Payment Summary')]",
				"titlePaymentSummary")

		TestObject checkboxLoans = findTestObject('Object Repository/18_finform/05_loanPage/05_checkbox_loans')
		TestObject btnPayNow = findTestObject('Object Repository/18_finform/05_loanPage/06_button_payNow')

		WebUI.click(checkboxLoans)

		boolean isTitlePaymentSummary = WebUI.verifyElementPresent(titlePaymentSummary, 10)

		if(!isTitlePaymentSummary) {
			KeywordUtil.markFailed("[ERROR] Title Payment Summary is not present.")
			throw new Exception("[ERROR] Step stop: Title not found.")
		}

		WebUI.delay(2)

		WebUI.click(btnPayNow)

		return this
	}

	@Keyword
	public FinformKeywords continuePayment() {
		TestObject btnPayment = findTestObject('Object Repository/18_finform/05_loanPage/07_button_continue')

		boolean isBtnPayment = WebUI.verifyElementPresent(btnPayment, 10)

		if(!isBtnPayment) {
			KeywordUtil.markFailed("[ERROR] Failed click button CONTINUE.")
			throw new Exception("[ERROR] Step stop: Button CONTINUE is disabled or not found.")
		}

		WebUI.click(btnPayment)

		return this
	}

	@Keyword
	public FinformKeywords choosePaymentMethod() {

		TestObject btnOkPayment = HelpersUtility.createTestObjectWithID("xpath", "//button[normalize-space()='OK']", "buttonOKPopupPayment")

		TestObject appMethodPayCard = findTestObject('Object Repository/18_finform/05_loanPage/08_appMethodPayCard')

		boolean isAppMethodPayCard = WebUI.verifyElementPresent(appMethodPayCard, 10)

		if(!isAppMethodPayCard) {
			KeywordUtil.markFailed("[ERROR] Failed click Method Payment.")
			throw new Exception("[ERROR] Step stop: Method Payment is disabled or not found.")
		}

		WebUI.click(appMethodPayCard)

		WebUI.click(btnOkPayment)

		return this
	}

	@Keyword
	public FinformKeywords goToMenuRegister() {
		
		TestObject menuRegisterObj = findTestObject('Object Repository/18_finform/03_registerPage/01_menu_register')
		
		waitForAndClick(menuRegisterObj, "REGISTER MENU")

		return this
	}

	@Keyword
	public FinformKeywords inputMobileNumber(String mobNum) {
		
		TestObject fieldMobileNumberObj = findTestObject('Object Repository/18_finform/03_registerPage/06_inputMobileNumber')
		
		waitForAndSetText(fieldMobileNumberObj, mobNum, "FIELD MOBILE NUMBER")
		
		return this
	}

	@Keyword
	public FinformKeywords inputMerchantCode(String merchCode) {
		boolean isSet = HelpersUtility.safeSetText(findTestObject('Object Repository/18_finform/03_registerPage/07_inputMerchantCode'), merchCode)

		if (!isSet) {
			KeywordUtil.markFailed("[ERROR] Failed input Merchant Code.")
			throw new Exception("[ERROR] Step stop: Field Merchant Code is disabled or not found.")
		}

		return this
	}

	@Keyword
	public String getMerchantCode() {
		String value = HelpersUtility.safeGetText(findTestObject('Object Repository/18_finform/03_registerPage/13_tableField_merchantCode'))

		if (value != null && !value.trim().isEmpty()) {
			KeywordUtil.logInfo("[INFO] Success get Merchant Code : " + value)
		} else {
			KeywordUtil.markFailed("[ERROR] Failed get text Merchant Code: Text not found.")
			throw new Exception("[ERROR] Step stop: Merchant Code null/empty.")
		}

		return value.trim()
	}

	@Keyword
	public FinformKeywords searchInquiry() {
		
		TestObject btnSearchInquiryObj = findTestObject('Object Repository/18_finform/buttonSearch')
		
		waitForAndClick(btnSearchInquiryObj, "BUTTON SEARCH INQUIRY")
		
		WebUI.delay(2)

		return this
	}

	@Keyword
	public FinformKeywords selectPartnerType(String partnerType) {
		
		TestObject fieldPartnerTypeObj = findTestObject('Object Repository/18_finform/03_registerPage/05_partnerType')
		
		waitForAndClick(fieldPartnerTypeObj, "FIELD PARTNER TYPE")

		WebUI.click(findTestObject('Object Repository/18_finform/03_registerPage/08_selectPartnerType', ['type': partnerType]))

		return this
	}


	@Keyword
	public FinformKeywords uploadTemplates(String pathFiles, String partnerType, String uploadType) {
		//		String fullPath = RunConfiguration.getProjectDir() + pathFiles.replace("/", File.separator)
		String fullPath = RunConfiguration.getProjectDir() + "${pathFiles}"

		TestObject uploadObj = findTestObject('Object Repository/18_finform/03_registerPage/09_uploadFile')
		TestObject btnUpload = findTestObject('Object Repository/18_finform/buttonUpload')
		TestObject btnRegisterUpload = findTestObject('Object Repository/18_finform/03_registerPage/15_buttonRegisterOutlet')
		TestObject btnOkSuccess = HelpersUtility.createTestObjectWithID("xpath", "//button[contains(@class,'btn-success')] | //button[text()='OK']", "okSuccess")
		TestObject alertMsg = HelpersUtility.createTestObjectWithID("xpath", "//*[contains(@class, 'alert-message')]", "alertMsg_uploadFailed")
		TestObject alertItems = HelpersUtility.createTestObjectWithID("xpath", "//ul[@class='alert-items']//li", "alertItems_uploadFailed")
		TestObject btnUploadRegister = HelpersUtility.createTestObjectWithID("xpath", "//span[contains(text(), 'Upload Register')]", "btnUploadRegister")

		if(partnerType == 'Customer - DEALER') {

			WebUI.click(btnUploadRegister)

			if (uploadType == 'Dealer') {
				WebUI.uploadFile(uploadObj, fullPath, FailureHandling.STOP_ON_FAILURE)
				WebUI.click(btnUpload)
			} else if (uploadType == 'Outlet') {
				WebUI.click(btnRegisterUpload)
				WebUI.uploadFile(uploadObj, fullPath, FailureHandling.STOP_ON_FAILURE)
				WebUI.click(btnUpload)
			} else {
				KeywordUtil.markFailedAndStop("[ERROR] uploadType '${uploadType}' unknown Customer - DEALER")
				throw new Exception("[ERROR] Step stop: uploadType not valid")
			}

		} else {

			WebUI.uploadFile(uploadObj, fullPath, FailureHandling.STOP_ON_FAILURE)
		}

		boolean isPopupPresent = WebUI.waitForElementPresent(btnOkSuccess, 15, FailureHandling.OPTIONAL)

		if (isPopupPresent) {
			KeywordUtil.logInfo("[INFO] Upload success detected via Popup")
		} else {

			if(partnerType == 'Customer - DEALER') {
				WebUI.waitForElementPresent(alertItems, 5, FailureHandling.OPTIONAL)
				String errorAlertItems = HelpersUtility.safeGetText(alertItems)

				KeywordUtil.markFailed("[ERROR] Upload Failed")
				throw new Exception("[ERROR] Step stop: Failed upload - ${errorAlertItems}")
			} else {
				WebUI.waitForElementPresent(alertMsg, 5, FailureHandling.OPTIONAL)
				String errorMessage = HelpersUtility.safeGetText(alertMsg)

				KeywordUtil.markFailed("[ERROR] Upload Failed")
				throw new Exception("[ERROR] Step stop: Failed upload - ${errorMessage}")

			}

		}

		return this

	}


	@Keyword
	public FinformKeywords verifyUpload() {
		TestObject btnOkSuccess = HelpersUtility.createTestObjectWithID("xpath", "//button[contains(@class,'btn-success')]", "okSuccess")
		TestObject btnOkFailed = HelpersUtility.createTestObjectWithID("xpath", "//button[contains(@class,'btn-error')]", "okFailed")
		TestObject alertMsgRegister = HelpersUtility.createTestObjectWithID("xpath", "//p[contains(@class, 'alert-message')]", "alertMsg_uploadFailedRegister")
		TestObject alertMsgOrder = HelpersUtility.createTestObjectWithID("xpath", "//p[contains(@class, 'alert-item-header')]//following::ul//li", "alertMsg_uploadFailedOrder")

		if (WebUI.verifyElementPresent(btnOkSuccess, 10, FailureHandling.OPTIONAL)) {
			HelpersUtility.safeClick(btnOkSuccess)
			KeywordUtil.logInfo("[INFO] Upload Success.")

		} else if (WebUI.verifyElementPresent(btnOkFailed, 10, FailureHandling.OPTIONAL)) {

			String errorTxt = ""

			if (WebUI.verifyElementPresent(alertMsgRegister, 3, FailureHandling.OPTIONAL)) {
				errorTxt = WebUI.getText(alertMsgRegister)
			} else if (WebUI.verifyElementPresent(alertMsgOrder, 3, FailureHandling.OPTIONAL)) {
				errorTxt = WebUI.getText(alertMsgOrder)
			} else {
				errorTxt = "Error Message not detected."
			}

			HelpersUtility.safeClick(btnOkFailed)
			KeywordUtil.markFailed("[ERROR] Upload Failed: " + errorTxt)
			throw new Exception("[ERROR] Step stop: Upload process return Error Button. Message: " + errorTxt)

		} else {
			KeywordUtil.markFailed("[ERROR] Failed upload: No response success / failed.")
			throw new Exception("[ERROR] Step stop: Upload response is not found..")
		}

		return this
	}

	@Keyword
	public FinformKeywords goToMenuOperations() {

		boolean isIconEditPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/18_finform/03_registerPage/10_iconEdit'), 10, FailureHandling.OPTIONAL)

		if(!isIconEditPresent) {
			KeywordUtil.markFailed('[ERROR] Failed to go to Form Edit Data.')
			throw new Exception('[ERROR] Step stop: Form Edit data is not found.')
		}

		WebUI.click(findTestObject('Object Repository/18_finform/03_registerPage/10_iconEdit'))

		return this
	}

	@Keyword
	public FinformKeywords goToMenuOperations_outlet() {

		TestObject outletObj = HelpersUtility.createTestObjectWithID("xpath", "//button[@type='button' and contains(normalize-space(), 'Outlet')]",  "tabOutlet Object")

		goToMenuOperations()

		boolean isOutlet = WebUI.verifyElementPresent(outletObj, 10)

		if(!isOutlet) {
			KeywordUtil.markFailed("[ERROR] Tab Outlet not clicked.")
			throw new Exception("[ERROR] Step stop: Tab Outlet was not found.")
		}

		WebUI.click(outletObj)
		
		return this

	}

	@Keyword
	public FinformKeywords goToMenuOperations_pks() {

		TestObject pksObj = HelpersUtility.createTestObjectWithID("xpath", "//button[@type='button' and contains(normalize-space(), 'PKS')]",  "tabPKS Object")

		goToMenuOperations()
		
		waitForAndClick(pksObj, "TAB PKS")

		return this

	}


	@Keyword
	public FinformKeywords clickUploadPksAction() {

		TestObject uploadPksObject = findTestObject('Object Repository/18_finform/06_dealerPKSPage/01_buttonUploadPKS')

		waitForAndClick(uploadPksObject, "ICON UPLOAD PKS")

		return this

	}

	@Keyword
	public FinformKeywords uploadPksFile(String pathPKSFile) {

		String pksFile = RunConfiguration.getProjectDir() + "${pathPKSFile}"

		KeywordUtil.logInfo("[INFO] Uploading file from: " + pksFile)

		TestObject fieldUploadPks = findTestObject('Object Repository/18_finform/fieldUploadFile')

		waitForAndUploadFile(fieldUploadPks, pksFile, "FIELD UPLOAD")
		
		WebUI.delay(1)

		return this

	}

	@Keyword
	public FinformKeywords selectActiveFrom(String activeFromDate) {

		TestObject fieldSelectActiveFromObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'activeFrom'])

		TestObject valueActiveFromObj = HelpersUtility.createTestObjectWithID(
				"xpath",
				"(//td[(@class='available' or  @class='active available end-date start-date today' or @class='available weekend') and normalize-space()='${activeFromDate}'])[1]",
				"activeFromValueObject")
		
		waitForAndClick(fieldSelectActiveFromObj, "ACTIVE FROM")
		waitForAndClick(valueActiveFromObj, "VALUE ACTIVE FROM")

		return this
	}

	@Keyword
	public FinformKeywords selectActiveTo(String activeToDate) {

		TestObject fieldSelectActiveToObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'activeTo'])

		TestObject valueActiveToObj = HelpersUtility.createTestObjectWithID(
				"xpath",
				"(//td[(@class='available' or  @class='active available end-date start-date today' or @class='available weekend') and normalize-space()='${activeToDate}'])[2]",
				"activeToObject")

		waitForAndClick(fieldSelectActiveToObj, "ACTIVE TO")
		waitForAndClick(valueActiveToObj, "VALUE ACTIVE TO")

		return this
	}

	@Keyword
	public FinformKeywords selectPKSMainDealer(String pksMd) {

		TestObject fieldSelectPKSMainDealerObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'pksMd'])

		TestObject valuePksMdObj = HelpersUtility.createTestObjectWithID(
				"xpath",
				"(//td[(@class='available' or  @class='active available end-date start-date today' or @class='available weekend') and normalize-space()='${pksMd}'])[3]",
				"activeFromObject")

		waitForAndClick(fieldSelectPKSMainDealerObj, "PKS MAIN DEALER")
		waitForAndClick(valuePksMdObj, "VALUE PKS MAIN DEALER")

		return this

	}

	@Keyword
	public FinformKeywords verifyAndCheckFlagDFS() {
		TestObject checkFlagDFSObj = HelpersUtility.createTestObjectWithID("xpath", "//input[@type='checkbox' and @formcontrolname='flagDfs']", "checkboxDFS")

		executeCheckboxLogic(checkFlagDFSObj, "DFS")

		return this
	}

	@Keyword
	public FinformKeywords verifyAndCheckFlagDFU() {
		TestObject checkFlagDFUObj = HelpersUtility.createTestObjectWithID("xpath", "//input[@type='checkbox' and @formcontrolname='flagDfu']", "checkboxDFU")

		executeCheckboxLogic(checkFlagDFUObj, "DFU")

		return this
	}

	@Keyword
	public FinformKeywords nextToCollateralDataPage() {
		TestObject btnNextObj = HelpersUtility.createTestObjectWithID("xpath", "//span[@class='truncate' and contains(normalize-space(),'Next')]", "btnNextObject")

		waitForAndClick(btnNextObj, "BUTTON NEXT")

		return this
	}

	

	@Keyword
	public FinformKeywords fillCollHeaderDataOrganization() {

		TestObject fieldOrganization = findTestObject('Object Repository/18_finform/06_dealerPKSPage/02_fieldOrganization')
		TestObject valueOrganization = findTestObject('Object Repository/18_finform/06_dealerPKSPage/03_targetValueOrganization')

		searchDropdownValue(fieldOrganization, valueOrganization)

		return this
	}

	@Keyword
	public FinformKeywords selectCollHeaderDataDocReceiveDate(String date) {

		TestObject fieldDocReceiveDateObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/04_fieldDocReceiveDate')
		TestObject selectDateObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/05_selectDocReceiveDate', [('date') : date])

		waitForAndClick(fieldDocReceiveDateObj, "FIELD DOC RECEIVED DATE")
		
		waitForAndClick(selectDateObj, "VALUE DOC RECEIVED DATE")

		return this
	}

	@Keyword
	public FinformKeywords fillCollHeaderDataDeliveredBy(String deliveredBy) {

		TestObject deliveredByObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/06_fieldDeliveredBy')

		waitForAndSetText(deliveredByObj, deliveredBy, "DELIVERED BY")

		return this

	}

	@Keyword
	public FinformKeywords fillCollHeaderDataDocReceivedBy(String receivedBy) {

		TestObject receivedByObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/07_fieldReceivedBy')

		waitForAndSetText(receivedByObj, receivedBy, "RECEIVED BY")

		return this

	}

	@Keyword
	public FinformKeywords selectCollHeaderDataDocLocation() {

		TestObject fieldDocLocation = findTestObject('Object Repository/18_finform/06_dealerPKSPage/08_fieldDocLocation')
		TestObject valueDocLocation = findTestObject('Object Repository/18_finform/06_dealerPKSPage/09_targetValueDocLocation')

		searchDropdownValue(fieldDocLocation, valueDocLocation)

		return this
	}

	@Keyword
	public FinformKeywords clickActionEditCollDetailDataList() {

		TestObject iconEditObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/10_iconEditAction')

		waitForAndClick(iconEditObj, "ICON ACTION EDIT")

		return this
	}

	@Keyword
	public FinformKeywords selectCollateralType() {

		TestObject fieldCollateralTypeObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/11_selectCollateralType')
		TestObject valueCollateralTypeObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/12_valueCollateralType')

		waitForAndClick(fieldCollateralTypeObj, "Collateral Type")
		waitForAndClick(valueCollateralTypeObj, "Value Collateral Type")

		return this

	}

	@Keyword
	public String fillNomorObjectPajak(String nop) {

		TestObject fieldNOPObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/13_dynamicInputFieldsCollateralInfo', [('fieldsName') : 'Nomor Object Pajak'])

		waitForAndSetText(fieldNOPObj, nop, "Nomor Object Pajak")

		return nop
	}

	@Keyword
	public String fillLuasTanah(String luasTanah) {

		TestObject fieldLuasTanahObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/13_dynamicInputFieldsCollateralInfo', [('fieldsName') : 'Luas Tanah'])

		waitForAndSetText(fieldLuasTanahObj, luasTanah, "Luas Tanah")

		return luasTanah
	}

	@Keyword
	public String fillStatusKepemilikan (String statusKepemilikan) {

		TestObject fieldStatusKepemilikanObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/13_dynamicInputFieldsCollateralInfo', [('fieldsName') : 'Status Kepemilikan'])

		waitForAndSetText(fieldStatusKepemilikanObj, statusKepemilikan, "Status Kepemilikan")

		return statusKepemilikan
	}

	@Keyword
	public String fillJenisSertifikat (String jenisSertifikat) {

		TestObject fieldJenisSertifikatObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/13_dynamicInputFieldsCollateralInfo', [('fieldsName') : 'Jenis Sertifikat'])

		waitForAndSetText(fieldJenisSertifikatObj, jenisSertifikat, "Jenis Sertifikat")

		return jenisSertifikat
	}

	@Keyword
	public String fillNoIdentifikasiBidang (String noIdentifikasiBidang) {

		TestObject fieldNoIdentifikasibidangObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/13_dynamicInputFieldsCollateralInfo', [('fieldsName') : 'Nomor Identifikasi Bidang'])

		waitForAndSetText(fieldNoIdentifikasibidangObj, noIdentifikasiBidang, "Nomor Identifikasi Bidang")

		return noIdentifikasiBidang
	}

	@Keyword
	public String fillLuasBangunan (String luasBangunan) {

		TestObject fieldLuasBangunanObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/13_dynamicInputFieldsCollateralInfo', [('fieldsName') : 'Luas Bangunan'])

		waitForAndSetText(fieldLuasBangunanObj, luasBangunan, "Luas Bangunan")

		return luasBangunan
	}

	@Keyword
	public String fillNoSuratUkur (String noSuratUkur) {

		TestObject fieldNoSuratUkurObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/13_dynamicInputFieldsCollateralInfo', [('fieldsName') : 'Nomor Surat Ukur'])

		waitForAndSetText(fieldNoSuratUkurObj, noSuratUkur, "Nomor Surat Ukur")

		return noSuratUkur
	}

	@Keyword
	public String fillNoSertifikat (String noSertifikat) {

		TestObject fieldNoSertifikatObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/13_dynamicInputFieldsCollateralInfo', [('fieldsName') : 'Nomor Sertifikat'])

		waitForAndSetText(fieldNoSertifikatObj, noSertifikat, "Nomor Sertifikat")

		return noSertifikat
	}

	@Keyword
	public String fillDocIssuedBy (String docIssuedBy) {

		TestObject fieldDocIssuedByObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/13_dynamicInputFieldsCollateralInfo', [('fieldsName') : 'Issued by'])

		waitForAndSetText(fieldDocIssuedByObj, docIssuedBy, "Issued by")

		return docIssuedBy
	}

	@Keyword
	public String fillDocumentNo (String documentNo) {

		TestObject fieldDocumentNoObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/13_dynamicInputFieldsCollateralInfo', [('fieldsName') : 'Document number'])

		waitForAndSetText(fieldDocumentNoObj, documentNo, "Document Number")

		return documentNo
	}

	@Keyword
	public String fillPhysicalAddress(String physicalAddress) {

		TestObject fieldPhysicalAddressObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'physicalAddress'])

		waitForAndSetText(fieldPhysicalAddressObj, physicalAddress, "PHYSICAL ADRESS")

		return physicalAddress

	}

	@Keyword
	public String fillRT(String rt) {

		TestObject fieldRTObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'rt'])

		waitForAndSetText(fieldRTObj, rt, "RT")

		return rt

	}

	@Keyword
	public String fillRW(String rw) {

		TestObject fieldRTObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'rw'])

		waitForAndSetText(fieldRTObj, rw, "RW")

		return rw

	}

	@Keyword
	public String selectProvince(String province) {

		TestObject fieldProvinceObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/15_dynamicAppCustomSelect', [('formName') : 'province'])
		TestObject valueProvinceObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/17_dynamicBtnValue', [('values') : province])

		waitForAndClick(fieldProvinceObj, "PROVINCE")
		waitForAndClick(valueProvinceObj, "VALUE PROVINCE")

		return province

	}

	@Keyword
	public String selectDATI2(String dati2) {

		TestObject fieldDati2Obj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/15_dynamicAppCustomSelect', [('formName') : 'dati2'])
		TestObject valueDati2Obj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/17_dynamicBtnValue', [('values') : dati2])

		waitForAndClick(fieldDati2Obj, "DATI2")
		waitForAndClick(valueDati2Obj, "VALUE DATI2")

		return dati2

	}

	@Keyword
	public String selectKecamatan(String kecamatan) {

		TestObject fieldKecamatanObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/15_dynamicAppCustomSelect', [('formName') : 'kecamatan'])
		TestObject valueKecamatanObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/17_dynamicBtnValue', [('values') : kecamatan])

		waitForAndClick(fieldKecamatanObj, "KECAMATAN")
		waitForAndClick(valueKecamatanObj, "VALUE KECAMATAN")

		return kecamatan

	}

	@Keyword
	public String selectKelurahan(String kelurahan) {

		TestObject fieldKelurahanObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/15_dynamicAppCustomSelect', [('formName') : 'kelurahan'])
		TestObject valueKelurahanObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/17_dynamicBtnValue', [('values') : kelurahan])

		waitForAndClick(fieldKelurahanObj, "KELURAHAN")
		waitForAndClick(valueKelurahanObj, "VALUE KELURAHAN")

		return kelurahan

	}

	@Keyword
	public String selectZipCode(String zipCode) {

		TestObject fieldZipCodeObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/16_appSearchableSelectZipCode')
		TestObject valueZipCodeObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/17_dynamicBtnValue', [('values') : zipCode])

		WebUI.delay(1)
		
		waitForAndClick(fieldZipCodeObj, "ZIP CODE")
		WebUI.delay(1)
		waitForAndClick(valueZipCodeObj, "VALUE ZIP CODE")

		return zipCode

	}

	@Keyword
	public String selectJenisPengikatan(String jenisPengikatan) {

		TestObject fieldJenisPengikatanObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/15_dynamicAppCustomSelect', [('formName') : 'jenisPengikatan'])
		TestObject valueJenisPengikatanObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/17_dynamicBtnValue', [('values') : jenisPengikatan])

		waitForAndClick(fieldJenisPengikatanObj, "JENIS PENGIKATAN")
		waitForAndClick(valueJenisPengikatanObj, "VALUE JENIS PENGIKATAN")

		return jenisPengikatan
	}

	@Keyword
	public FinformKeywords selectTanggalPengikatan(String tanggalPengikatan) {

		TestObject fieldTanggalPengikatanObj = HelpersUtility.createTestObjectWithID("xpath", "//input[@formcontrolname='tanggalPengikatan']", "tanggalPengikatanObject")

		TestObject valueTanggalPengikatanObj = HelpersUtility.createTestObjectWithID(
				"xpath",
				"(//td[(@class='available' or  @class='active available end-date start-date today' or @class='available weekend') and normalize-space()='${tanggalPengikatan}'])[2]",
				"valueTanggalPengikatanObject")

		waitForAndClick(fieldTanggalPengikatanObj, "TANGGAL PENGIKATAN")
		WebUI.scrollToElement(fieldTanggalPengikatanObj, 10)
		waitForAndClick(valueTanggalPengikatanObj, "VALUE TANGGAL PENGIKATAN")

		return this
	}

	@Keyword
	public FinformKeywords checkFlagInsured() {

		TestObject checkboxObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'flagIssued'])

		waitForAndClick(checkboxObj, "CHECKBOX FLAG INSURED")

		return this
	}

	@Keyword
	public String fillIdentityNo(String identityNo) {

		TestObject fieldIdentityNoObj =findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'identityNo'])

		waitForAndSetText(fieldIdentityNoObj, identityNo, "IDENTITY NO")

		return identityNo
	}

	@Keyword
	public String fillMarketValue(String marketValue) {

		TestObject fieldMarketValueObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'marketValue'])

		waitForAndSetText(fieldMarketValueObj, marketValue, "MARKET VALUE")

		return marketValue
	}

	@Keyword
	public String fillInternalAppraiserValue(String internalAppraiserValue) {

		TestObject fieldInternalAppraiserValueObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'internalAppraiserValue'])

		waitForAndSetText(fieldInternalAppraiserValueObj, internalAppraiserValue, "INTERNAL APPRAISER VALUE")

		return internalAppraiserValue
	}


	@Keyword
	public FinformKeywords selectInternalAppraiserDate(String dateInternalAppraiser) {

		TestObject fieldInternalAppraiserDateObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'internalAppraiserDate'])

		TestObject valueInternalAppraiserDateObj = HelpersUtility.createTestObjectWithID(
				"xpath",
				"(//td[(@class='available' or  @class='active available end-date start-date today' or @class='available weekend') and normalize-space()='${dateInternalAppraiser}'])[3]",
				"valueInternalAppraiserDateObj")

		waitForAndClick(fieldInternalAppraiserDateObj, "INTERNAL APPRAISER DATE")
		WebUI.waitForElementVisible(valueInternalAppraiserDateObj, 10)
		WebUI.waitForElementClickable(valueInternalAppraiserDateObj, 10)
		WebUI.click(valueInternalAppraiserDateObj)

		return this

	}

	@Keyword
	public FinformKeywords selectExternalAppraiserDate(String dateExternalAppraiser) {

		TestObject fieldExternalAppraiserDateObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'externalAppraiserDate'])

		TestObject valueExternalAppraiserDateObj = HelpersUtility.createTestObjectWithID(
				"xpath",
				"(//td[(@class='available' or  @class='active available end-date start-date today' or @class='available weekend') and normalize-space()='${dateExternalAppraiser}'])[4]",
				"valueExternalAppraiserDateObj")

		waitForAndClick(fieldExternalAppraiserDateObj, "EXTERNAL APPRAISER DATE")
		WebUI.waitForElementVisible(valueExternalAppraiserDateObj, 10)
		WebUI.waitForElementClickable(valueExternalAppraiserDateObj, 10)
		WebUI.click(valueExternalAppraiserDateObj)

		return this

	}

	@Keyword
	public String fillOwnershipNameOn(String ownershipNameOn) {

		TestObject fieldOwnershipNameOnObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'ownershipNameOn'])

		waitForAndSetText(fieldOwnershipNameOnObj, ownershipNameOn, "OWNERSHIP NAME ON")

		return ownershipNameOn

	}

	@Keyword
	public String fillExternalAppraiserValue(String externalAppraiserValue) {

		TestObject fieldExternalAppraiserValueObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'externalAppraiserValue'])

		waitForAndSetText(fieldExternalAppraiserValueObj, externalAppraiserValue, "EXTERNAL APPRAISER VALUE")

		return externalAppraiserValue

	}

	@Keyword
	public String fillExternalAppraiser(String externalAppraiser) {

		TestObject fieldExternalAppraiserObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'externalAppraiser'])

		waitForAndSetText(fieldExternalAppraiserObj, externalAppraiser, "EXTERNAL APPRAISER")

		return externalAppraiser

	}

	@Keyword
	public String fillCollateralPremi(String collateralPremi) {

		TestObject fieldCollateralPremiObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'collateralPremi'])

		waitForAndSetText(fieldCollateralPremiObj, collateralPremi, "COLLATERAL PREMI")

		return collateralPremi
	}

	public void saveCollateralDetailData() {

		TestObject btnSaveObj = findTestObject('Object Repository/18_finform/dynamicButton', [('text') : 'Save'])

		waitForAndClick(btnSaveObj, "BUTTON SAVE")

	}
	
	@Keyword
	public void nextToDocumentAttachment() {
		
		TestObject btnNextObj = findTestObject('Object Repository/18_finform/dynamicButton', [('text') : 'Next'])
		
		WebUI.delay(2)
		waitForAndClick(btnNextObj, "BUTTON NEXT")
		
	}
	
	@Keyword
	public void uploadDocumentAttachment(String pathDocAttchment) {
		
		String path = RunConfiguration.getProjectDir() + "${pathDocAttchment}"

		TestObject fieldInputObj = findTestObject('Object Repository/18_finform/fieldUploadFile')

		boolean isFieldInputObj = WebUI.waitForElementPresent(fieldInputObj, 10, FailureHandling.OPTIONAL)

		if(!isFieldInputObj) {

			KeywordUtil.markFailed("[ERROR] Element Upload File not clicked.")
			throw new Exception("[ERROR] Step stop: Element Upload File was not found.")

		}
		
		try {
			WebUI.uploadFile(fieldInputObj, path, FailureHandling.STOP_ON_FAILURE)

			KeywordUtil.logInfo("[INFO] File successfully uploaded: " + path)

		} catch (Exception e) {
			KeywordUtil.markFailed("[ERROR] File failed to upload. Path: " + path + ". Reason: " + e.getMessage())
			throw new Exception("[ERROR] Step stop: Upload process failed.")
		}

	}
	
	@Keyword
	public String selectDocType(String docType) {
		
		TestObject fieldSelectDocTypeObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/15_dynamicAppCustomSelect')

		String baseXpath = fieldSelectDocTypeObj.getSelectorCollection().get(SelectorMethod.XPATH)

		String injectedXpath = baseXpath.replace('${formName}', 'docType')

		String newXpath = injectedXpath + "/div/button"
		KeywordUtil.logInfo("[INFO] Generated Dynamic XPath: " + newXpath)

		TestObject finalFieldSelectDocTypeObj = HelpersUtility.createTestObjectWithID("xpath", newXpath, "fieldSelectDocType")

		TestObject valueDocTypeObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/17_dynamicBtnValue', [('values') : docType])

		waitForAndClick(finalFieldSelectDocTypeObj, "DOC TYPE")
		waitForAndClick(valueDocTypeObj, "VALUE DOC TYPE")
		
		return docType
		
	}
	
	@Keyword
	public String fillDocDesc(String docDesc) {
		
		TestObject fieldDocDescObj = findTestObject('Object Repository/18_finform/06_dealerPKSPage/14_dynamicInputFields', [('formName') : 'docDesc'])
		
		waitForAndSetText(fieldDocDescObj, docDesc, "DOC DESC")
		
		return docDesc

	}
	
	@Keyword
	public FinformKeywords submitPks() {
		
		TestObject btnSubmitObj = findTestObject('Object Repository/18_finform/dynamicButton', [('text') : 'Submit'])
		
		waitForAndClick(btnSubmitObj, "BUTTON SUBMIT")
		
		return this
		
	}

	@Keyword
	public FinformKeywords verifyUpdatePassword() {

		TestObject msg = HelpersUtility.createTestObjectWithID("xpath", "//h2[@class='alert-title']", "msg")
		TestObject btn = HelpersUtility.createTestObjectWithID("xpath", "//button[contains(@class,'btn-success')]", "btnOk")

		boolean visible = WebUI.verifyElementPresent(msg, 10, FailureHandling.OPTIONAL)

		String msgText = WebUI.getText(msg)

		if (!visible) {
			throw new Exception("[ERROR] ${msgText}")
		}

		KeywordUtil.logInfo("[INFO] ${msgText}")
		WebUI.click(btn)

		return this

	}

	@Keyword
	public FinformKeywords setPassword(String password) {

		boolean isInputSuccess = HelpersUtility.safeSetText(
				findTestObject('Object Repository/18_finform/03_registerPage/11_fieldEditPassword'),
				password
				)

		if (!isInputSuccess) {
			KeywordUtil.markFailedAndStop("[ERROR] Failed input password!")
			throw new Exception("[ERROR] Step Stop: Input password failed, can not proceed to Save button.")
		}

		WebUI.click(findTestObject('Object Repository/18_finform/03_registerPage/12_btnSaveEditData'))

		return this
	}

	@Keyword
	public FinformKeywords uploadCreateInvoice() {
		TestObject btnUpload = HelpersUtility.createTestObjectWithID('xpath', "//button[span[contains(@class,'truncate') and normalize-space()='UPLOAD']]", 'buttonUpload')

		boolean isBtnUpload = HelpersUtility.safeClickFailHandling(btnUpload, 10, FailureHandling.OPTIONAL)

		if(!isBtnUpload) {
			KeywordUtil.markFailed("[ERROR] Failed click button Upload at Create Invoice Section")
			throw new Exception("[ERROR] Step stop: Failed upload file for create Invoice")
		}

		return this
	}

	@Keyword
	public FinformKeywords uploadFileTxt(String pathFiles) {
		//		String fullPath = RunConfiguration.getProjectDir() + File.separator + pathFiles.replace("/", File.separator)
		String fullPath = RunConfiguration.getProjectDir() + "${pathFiles}"

		KeywordUtil.logInfo("[INFO] Uploading file from: " + fullPath)

		new File(fullPath).exists()

		boolean upload = WebUI.uploadFile(findTestObject('Object Repository/18_finform/04_invoicePage/01_uploadFileTxt'), fullPath)

		return this
	}

	@Keyword
	public FinformKeywords uploadFileXlsx(String pathFiles) {
		//		String fullPath = RunConfiguration.getProjectDir() + File.separator + pathFiles.replace("/", File.separator)
		String fullPath = RunConfiguration.getProjectDir() + "${pathFiles}"

		KeywordUtil.logInfo("Uploading file from: " + fullPath)

		new File(fullPath).exists()

		boolean upload = WebUI.uploadFile(findTestObject('Object Repository/18_finform/04_invoicePage/02_inputFileXlsxs'), fullPath)

		return this

	}

	@Keyword
	public String searchLoanNumber(String invoiceNo) {
		TestObject input = findTestObject('Object Repository/18_finform/04_invoicePage/03_field_invoiceNumber')
		TestObject loanObj = findTestObject('Object Repository/18_finform/04_invoicePage/04_tableField_loanNumber')

		HelpersUtility.safeSetText(input, invoiceNo)

		searchInquiry()

		WebUI.waitForElementPresent(loanObj, 10)
		String loanNumber = HelpersUtility.safeGetText(loanObj)

		if (loanNumber == null || loanNumber.isEmpty()) {
			KeywordUtil.markFailedAndStop("[ERROR] Loan Number tidak ditemukan!")
			throw new Exception("[ERROR] Loan Number is not shown in tabel")
		}

		KeywordUtil.logInfo("Success get Loan Number: " + loanNumber)

		return loanNumber.trim()

	}

	@Keyword
	public FinformKeywords uploadByPartnerType(String type, String pathAHASS, String pathHSO, String uploadType) {
		String path = (type == 'Customer - AHASS') ? pathAHASS : pathHSO
		return uploadTemplates(path, type, uploadType)
	}

	@Keyword
	public FinformKeywords inputMerchantByPartnerType(String type, String cmCode, String randomStr) {
		String code = (type == 'Customer - AHASS') ? cmCode : randomStr
		return inputMerchantCode(code)
	}


}
