import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

import path from 'path';

const expect = chai.expect;

const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
const absolutePath = path.resolve(__dirname, fileToUpload);
export default class EmployeeUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.employee.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  titleSelect: ElementFinder = element(by.css('select#employee-title'));
  firstNameInput: ElementFinder = element(by.css('input#employee-firstName'));
  middleInitialInput: ElementFinder = element(by.css('input#employee-middleInitial'));
  lastNameInput: ElementFinder = element(by.css('input#employee-lastName'));
  preferredNameInput: ElementFinder = element(by.css('input#employee-preferredName'));
  genderSelect: ElementFinder = element(by.css('select#employee-gender'));
  employeeCodeInput: ElementFinder = element(by.css('input#employee-employeeCode'));
  socialSecurityNumberInput: ElementFinder = element(by.css('input#employee-socialSecurityNumber'));
  pinCodeInput: ElementFinder = element(by.css('input#employee-pinCode'));
  transportModeSelect: ElementFinder = element(by.css('select#employee-transportMode'));
  addressInput: ElementFinder = element(by.css('input#employee-address'));
  countyInput: ElementFinder = element(by.css('input#employee-county'));
  postCodeInput: ElementFinder = element(by.css('input#employee-postCode'));
  dateOfBirthInput: ElementFinder = element(by.css('input#employee-dateOfBirth'));
  photoInput: ElementFinder = element(by.css('input#file_photo'));
  photoUrlInput: ElementFinder = element(by.css('input#employee-photoUrl'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#employee-lastUpdatedDate'));
  tenantIdInput: ElementFinder = element(by.css('input#employee-tenantId'));
  userSelect: ElementFinder = element(by.css('select#employee-user'));
  nationalitySelect: ElementFinder = element(by.css('select#employee-nationality'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTitleSelect(title) {
    await this.titleSelect.sendKeys(title);
  }

  async getTitleSelect() {
    return this.titleSelect.element(by.css('option:checked')).getText();
  }

  async titleSelectLastOption() {
    await this.titleSelect.all(by.tagName('option')).last().click();
  }
  async setFirstNameInput(firstName) {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput() {
    return this.firstNameInput.getAttribute('value');
  }

  async setMiddleInitialInput(middleInitial) {
    await this.middleInitialInput.sendKeys(middleInitial);
  }

  async getMiddleInitialInput() {
    return this.middleInitialInput.getAttribute('value');
  }

  async setLastNameInput(lastName) {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput() {
    return this.lastNameInput.getAttribute('value');
  }

  async setPreferredNameInput(preferredName) {
    await this.preferredNameInput.sendKeys(preferredName);
  }

  async getPreferredNameInput() {
    return this.preferredNameInput.getAttribute('value');
  }

  async setGenderSelect(gender) {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect() {
    return this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption() {
    await this.genderSelect.all(by.tagName('option')).last().click();
  }
  async setEmployeeCodeInput(employeeCode) {
    await this.employeeCodeInput.sendKeys(employeeCode);
  }

  async getEmployeeCodeInput() {
    return this.employeeCodeInput.getAttribute('value');
  }

  async setSocialSecurityNumberInput(socialSecurityNumber) {
    await this.socialSecurityNumberInput.sendKeys(socialSecurityNumber);
  }

  async getSocialSecurityNumberInput() {
    return this.socialSecurityNumberInput.getAttribute('value');
  }

  async setPinCodeInput(pinCode) {
    await this.pinCodeInput.sendKeys(pinCode);
  }

  async getPinCodeInput() {
    return this.pinCodeInput.getAttribute('value');
  }

  async setTransportModeSelect(transportMode) {
    await this.transportModeSelect.sendKeys(transportMode);
  }

  async getTransportModeSelect() {
    return this.transportModeSelect.element(by.css('option:checked')).getText();
  }

  async transportModeSelectLastOption() {
    await this.transportModeSelect.all(by.tagName('option')).last().click();
  }
  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return this.addressInput.getAttribute('value');
  }

  async setCountyInput(county) {
    await this.countyInput.sendKeys(county);
  }

  async getCountyInput() {
    return this.countyInput.getAttribute('value');
  }

  async setPostCodeInput(postCode) {
    await this.postCodeInput.sendKeys(postCode);
  }

  async getPostCodeInput() {
    return this.postCodeInput.getAttribute('value');
  }

  async setDateOfBirthInput(dateOfBirth) {
    await this.dateOfBirthInput.sendKeys(dateOfBirth);
  }

  async getDateOfBirthInput() {
    return this.dateOfBirthInput.getAttribute('value');
  }

  async setPhotoInput(photo) {
    await this.photoInput.sendKeys(photo);
  }

  async getPhotoInput() {
    return this.photoInput.getAttribute('value');
  }

  async setPhotoUrlInput(photoUrl) {
    await this.photoUrlInput.sendKeys(photoUrl);
  }

  async getPhotoUrlInput() {
    return this.photoUrlInput.getAttribute('value');
  }

  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
  }

  async setTenantIdInput(tenantId) {
    await this.tenantIdInput.sendKeys(tenantId);
  }

  async getTenantIdInput() {
    return this.tenantIdInput.getAttribute('value');
  }

  async userSelectLastOption() {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option) {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect() {
    return this.userSelect;
  }

  async getUserSelectedOption() {
    return this.userSelect.element(by.css('option:checked')).getText();
  }

  async nationalitySelectLastOption() {
    await this.nationalitySelect.all(by.tagName('option')).last().click();
  }

  async nationalitySelectOption(option) {
    await this.nationalitySelect.sendKeys(option);
  }

  getNationalitySelect() {
    return this.nationalitySelect;
  }

  async getNationalitySelectedOption() {
    return this.nationalitySelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.titleSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setFirstNameInput('firstName');
    expect(await this.getFirstNameInput()).to.match(/firstName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setMiddleInitialInput('middleInitial');
    expect(await this.getMiddleInitialInput()).to.match(/middleInitial/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastNameInput('lastName');
    expect(await this.getLastNameInput()).to.match(/lastName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setPreferredNameInput('preferredName');
    expect(await this.getPreferredNameInput()).to.match(/preferredName/);
    await waitUntilDisplayed(this.saveButton);
    await this.genderSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setEmployeeCodeInput('employeeCode');
    expect(await this.getEmployeeCodeInput()).to.match(/employeeCode/);
    await waitUntilDisplayed(this.saveButton);
    await this.setSocialSecurityNumberInput('socialSecurityNumber');
    expect(await this.getSocialSecurityNumberInput()).to.match(/socialSecurityNumber/);
    await waitUntilDisplayed(this.saveButton);
    await this.setPinCodeInput('5');
    expect(await this.getPinCodeInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.transportModeSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setAddressInput('address');
    expect(await this.getAddressInput()).to.match(/address/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCountyInput('county');
    expect(await this.getCountyInput()).to.match(/county/);
    await waitUntilDisplayed(this.saveButton);
    await this.setPostCodeInput('postCode');
    expect(await this.getPostCodeInput()).to.match(/postCode/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDateOfBirthInput('01-01-2001');
    expect(await this.getDateOfBirthInput()).to.eq('2001-01-01');
    await waitUntilDisplayed(this.saveButton);
    await this.setPhotoInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.setPhotoUrlInput('photoUrl');
    expect(await this.getPhotoUrlInput()).to.match(/photoUrl/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantIdInput('5');
    expect(await this.getTenantIdInput()).to.eq('5');
    await this.userSelectLastOption();
    await this.nationalitySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
