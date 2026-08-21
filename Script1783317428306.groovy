import id.co.finform.FinformKeywords as Finforms
import utils.HelpersUtility as WEBUTILS
import utils.DataGenerator as DataGenerator
import utils.FileUtils as FileUtils
import utils.DataGenerator as DataGenerator

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.util.KeywordUtil
import java.text.SimpleDateFormat
import java.util.Date
import com.github.javafaker.Faker
import java.nio.file.*
import java.util.Random

import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.Dimension
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.AfterTestCase

TestData addPksData = findTestData('Data Files/Finform/08_addNewPks')

DataGenerator dataGenerator = new DataGenerator()

String pksActiveFrom = dataGenerator.generateCustomDate(0, 'dd')
KeywordUtil.logInfo(pksActiveFrom)

String pksActiveTo = dataGenerator.generateCustomDate(7, 'dd')
KeywordUtil.logInfo(pksActiveTo)

String pksMdDate = dataGenerator.generateCustomDate(0, 'dd')
KeywordUtil.logInfo(pksMdDate)

String usernameAdmin = addPksData.getValue('Username Admin', 1)
String passwordAdmin = addPksData.getValue('Password Admin', 1)

String partnerType = addPksData.getValue('Partner Type', 1)
String mobNum = addPksData.getValue('Mobile Number', 1)

String pathPKSFile = addPksData.getValue('Path PKS File', 1)
String receiveDate = addPksData.getValue('Receive Date', 1)
String deliveredBy = addPksData.getValue('Delivered By', 1)
String receivedBy = addPksData.getValue('Received By', 1)
String nop = addPksData.getValue('Nomor Objek Pajak', 1)
String luasTanah = addPksData.getValue('Luas Tanah', 1)
String statusKepemilikan = addPksData.getValue('Status Kepemilikan', 1)
String jenisSertifikat = addPksData.getValue('Jenis Sertifikat', 1)
String noIdentifikasiBidang = addPksData.getValue('No Identifikasi Bidang', 1)
String luasBangunan = addPksData.getValue('Luas Bangunan', 1)
String noSuratUkur = addPksData.getValue('No Surat Ukur', 1)
String noSertifikat = addPksData.getValue('No Sertifikat', 1)
String docIssuedBy = addPksData.getValue('Document Issued By', 1)
String documentNo = addPksData.getValue('Document No', 1)
String physicalAddress = addPksData.getValue('Physical Address', 1)
String rt = addPksData.getValue('RT', 1)
String rw = addPksData.getValue('RW', 1)
String province = addPksData.getValue('Provinsi', 1)
String dati2 = addPksData.getValue('DATI2', 1)
String kecamatan = addPksData.getValue('Kecamatan', 1)
String kelurahan = addPksData.getValue('Kelurahan', 1)
String zipCode = addPksData.getValue('Zip Code', 1)
String jenisPengikatan = addPksData.getValue('Jenis Pengikatan', 1)
String tanggalPengikatan = addPksData.getValue('Tanggal Pengikatan', 1)
String identityNo = addPksData.getValue('Identitiy No', 1)
String marketValue = addPksData.getValue('Market Value', 1)
String internalAppraiserValue = addPksData.getValue('Internal Appraiser Value', 1)
String ownershipNameOn = addPksData.getValue('Ownership Name ON', 1)
String externalAppraiserValue = addPksData.getValue('External Appraiser Value', 1)
String externalAppraiser = addPksData.getValue('External Appraiser', 1)
String collateralPremi = addPksData.getValue('Collateral Premi', 1)
String internalAppraiserDate = addPksData.getValue('Internal Appraiser Date', 1)
String externalAppraiserDate = addPksData.getValue('External Appraiser Date', 1)
String activeFromDate = pksActiveFrom //addPksData.getValue('Active From Date', 1)
String activeToDate = pksActiveTo //addPksData.getValue('Active To Date', 1)
String pksMd = pksMdDate //addPksData.getValue('PKS Main Dealer', 1)
String pathDocAttchment = addPksData.getValue('Path Doc Attachment', 1)
String docType = addPksData.getValue('Doc Type', 1)
String docDesc =addPksData.getValue('Doc Desc', 1)


Finforms finform = new Finforms(GlobalVariable.finformURL)

String getMobileNumber = finform.getData('mobileNumber')

finform.loginFinform(usernameAdmin, passwordAdmin)
		.goToMenuRegister()
		.selectPartnerType(partnerType)
		.inputMobileNumber(getMobileNumber) //getMobileNumber //mobNum
		.searchInquiry()
		.goToMenuOperations_pks()
		.clickUploadPksAction()
		.uploadPksFile(pathPKSFile)
		.selectActiveFrom(activeFromDate)
		.selectActiveTo(activeToDate)
		.selectPKSMainDealer(pksMd)
		.verifyAndCheckFlagDFS()
		.verifyAndCheckFlagDFU()
		.nextToCollateralDataPage()
		
		
// Fill all the Collateral Detail Data //
finform.fillCollHeaderDataOrganization()
finform.selectCollHeaderDataDocReceiveDate(receiveDate)
finform.fillCollHeaderDataDeliveredBy(deliveredBy)
finform.fillCollHeaderDataDocReceivedBy(receivedBy)
finform.selectCollHeaderDataDocLocation()
finform.clickActionEditCollDetailDataList()
finform.selectCollateralType()
finform.fillNomorObjectPajak(nop)
finform.fillLuasTanah(luasTanah)
finform.fillStatusKepemilikan(statusKepemilikan)
finform.fillJenisSertifikat(jenisSertifikat)
finform.fillNoIdentifikasiBidang(noIdentifikasiBidang)
finform.fillLuasBangunan(luasBangunan)
finform.fillNoSuratUkur(noSuratUkur)
finform.fillNoSertifikat(noSertifikat)
finform.fillDocIssuedBy(docIssuedBy)
finform.fillDocumentNo(documentNo)
finform.fillPhysicalAddress(physicalAddress)
finform.fillRT(rt)
finform.fillRW(rw)
finform.selectProvince(province)
finform.selectDATI2(dati2)
finform.selectKecamatan(kecamatan)
finform.selectKelurahan(kelurahan)
finform.selectZipCode(zipCode)
finform.selectJenisPengikatan(jenisPengikatan)
finform.selectTanggalPengikatan(tanggalPengikatan)
finform.checkFlagInsured()
finform.fillIdentityNo(identityNo)
finform.fillMarketValue(marketValue)
finform.fillInternalAppraiserValue(internalAppraiserValue)
finform.selectInternalAppraiserDate(internalAppraiserDate)
finform.fillOwnershipNameOn(ownershipNameOn)
finform.fillExternalAppraiserValue(externalAppraiserValue)
finform.selectExternalAppraiserDate(externalAppraiserDate)
finform.fillExternalAppraiser(externalAppraiser)
finform.fillCollateralPremi(collateralPremi)
finform.saveCollateralDetailData()
finform.nextToDocumentAttachment()
finform.uploadDocumentAttachment(pathDocAttchment)
finform.selectDocType(docType)
finform.fillDocDesc(docDesc)
finform.submitPks()

finform.clearAllData()

WebUI.delay(5)

