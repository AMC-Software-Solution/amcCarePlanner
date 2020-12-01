import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class MedicalContactUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.medicalContact.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  doctorNameInput: ElementFinder = element(by.css('input#medical-contact-doctorName'));
  doctorSurgeryInput: ElementFinder = element(by.css('input#medical-contact-doctorSurgery'));
  doctorAddressInput: ElementFinder = element(by.css('input#medical-contact-doctorAddress'));
  doctorPhoneInput: ElementFinder = element(by.css('input#medical-contact-doctorPhone'));
  lastVisitedDoctorInput: ElementFinder = element(by.css('input#medical-contact-lastVisitedDoctor'));
  districtNurseNameInput: ElementFinder = element(by.css('input#medical-contact-districtNurseName'));
  districtNursePhoneInput: ElementFinder = element(by.css('input#medical-contact-districtNursePhone'));
  careManagerNameInput: ElementFinder = element(by.css('input#medical-contact-careManagerName'));
  careManagerPhoneInput: ElementFinder = element(by.css('input#medical-contact-careManagerPhone'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#medical-contact-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#medical-contact-clientId'));
  serviceUserSelect: ElementFinder = element(by.css('select#medical-contact-serviceUser'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setDoctorNameInput(doctorName) {
    await this.doctorNameInput.sendKeys(doctorName);
  }

  async getDoctorNameInput() {
    return this.doctorNameInput.getAttribute('value');
  }

  async setDoctorSurgeryInput(doctorSurgery) {
    await this.doctorSurgeryInput.sendKeys(doctorSurgery);
  }

  async getDoctorSurgeryInput() {
    return this.doctorSurgeryInput.getAttribute('value');
  }

  async setDoctorAddressInput(doctorAddress) {
    await this.doctorAddressInput.sendKeys(doctorAddress);
  }

  async getDoctorAddressInput() {
    return this.doctorAddressInput.getAttribute('value');
  }

  async setDoctorPhoneInput(doctorPhone) {
    await this.doctorPhoneInput.sendKeys(doctorPhone);
  }

  async getDoctorPhoneInput() {
    return this.doctorPhoneInput.getAttribute('value');
  }

  async setLastVisitedDoctorInput(lastVisitedDoctor) {
    await this.lastVisitedDoctorInput.sendKeys(lastVisitedDoctor);
  }

  async getLastVisitedDoctorInput() {
    return this.lastVisitedDoctorInput.getAttribute('value');
  }

  async setDistrictNurseNameInput(districtNurseName) {
    await this.districtNurseNameInput.sendKeys(districtNurseName);
  }

  async getDistrictNurseNameInput() {
    return this.districtNurseNameInput.getAttribute('value');
  }

  async setDistrictNursePhoneInput(districtNursePhone) {
    await this.districtNursePhoneInput.sendKeys(districtNursePhone);
  }

  async getDistrictNursePhoneInput() {
    return this.districtNursePhoneInput.getAttribute('value');
  }

  async setCareManagerNameInput(careManagerName) {
    await this.careManagerNameInput.sendKeys(careManagerName);
  }

  async getCareManagerNameInput() {
    return this.careManagerNameInput.getAttribute('value');
  }

  async setCareManagerPhoneInput(careManagerPhone) {
    await this.careManagerPhoneInput.sendKeys(careManagerPhone);
  }

  async getCareManagerPhoneInput() {
    return this.careManagerPhoneInput.getAttribute('value');
  }

  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
  }

  async setClientIdInput(clientId) {
    await this.clientIdInput.sendKeys(clientId);
  }

  async getClientIdInput() {
    return this.clientIdInput.getAttribute('value');
  }

  async serviceUserSelectLastOption() {
    await this.serviceUserSelect.all(by.tagName('option')).last().click();
  }

  async serviceUserSelectOption(option) {
    await this.serviceUserSelect.sendKeys(option);
  }

  getServiceUserSelect() {
    return this.serviceUserSelect;
  }

  async getServiceUserSelectedOption() {
    return this.serviceUserSelect.element(by.css('option:checked')).getText();
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
    await this.setDoctorNameInput('doctorName');
    expect(await this.getDoctorNameInput()).to.match(/doctorName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDoctorSurgeryInput('doctorSurgery');
    expect(await this.getDoctorSurgeryInput()).to.match(/doctorSurgery/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDoctorAddressInput('doctorAddress');
    expect(await this.getDoctorAddressInput()).to.match(/doctorAddress/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDoctorPhoneInput('doctorPhone');
    expect(await this.getDoctorPhoneInput()).to.match(/doctorPhone/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastVisitedDoctorInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastVisitedDoctorInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setDistrictNurseNameInput('districtNurseName');
    expect(await this.getDistrictNurseNameInput()).to.match(/districtNurseName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDistrictNursePhoneInput('districtNursePhone');
    expect(await this.getDistrictNursePhoneInput()).to.match(/districtNursePhone/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCareManagerNameInput('careManagerName');
    expect(await this.getCareManagerNameInput()).to.match(/careManagerName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCareManagerPhoneInput('careManagerPhone');
    expect(await this.getCareManagerPhoneInput()).to.match(/careManagerPhone/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.serviceUserSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
