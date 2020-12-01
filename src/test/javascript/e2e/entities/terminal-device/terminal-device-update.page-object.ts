import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class TerminalDeviceUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.terminalDevice.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  deviceNameInput: ElementFinder = element(by.css('input#terminal-device-deviceName'));
  deviceModelInput: ElementFinder = element(by.css('input#terminal-device-deviceModel'));
  registeredDateInput: ElementFinder = element(by.css('input#terminal-device-registeredDate'));
  imeiInput: ElementFinder = element(by.css('input#terminal-device-imei'));
  simNumberInput: ElementFinder = element(by.css('input#terminal-device-simNumber'));
  userStartedUsingFromInput: ElementFinder = element(by.css('input#terminal-device-userStartedUsingFrom'));
  deviceOnLocationFromInput: ElementFinder = element(by.css('input#terminal-device-deviceOnLocationFrom'));
  operatingSystemInput: ElementFinder = element(by.css('input#terminal-device-operatingSystem'));
  noteInput: ElementFinder = element(by.css('input#terminal-device-note'));
  ownerEntityIdInput: ElementFinder = element(by.css('input#terminal-device-ownerEntityId'));
  ownerEntityNameInput: ElementFinder = element(by.css('input#terminal-device-ownerEntityName'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#terminal-device-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#terminal-device-clientId'));
  employeeSelect: ElementFinder = element(by.css('select#terminal-device-employee'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setDeviceNameInput(deviceName) {
    await this.deviceNameInput.sendKeys(deviceName);
  }

  async getDeviceNameInput() {
    return this.deviceNameInput.getAttribute('value');
  }

  async setDeviceModelInput(deviceModel) {
    await this.deviceModelInput.sendKeys(deviceModel);
  }

  async getDeviceModelInput() {
    return this.deviceModelInput.getAttribute('value');
  }

  async setRegisteredDateInput(registeredDate) {
    await this.registeredDateInput.sendKeys(registeredDate);
  }

  async getRegisteredDateInput() {
    return this.registeredDateInput.getAttribute('value');
  }

  async setImeiInput(imei) {
    await this.imeiInput.sendKeys(imei);
  }

  async getImeiInput() {
    return this.imeiInput.getAttribute('value');
  }

  async setSimNumberInput(simNumber) {
    await this.simNumberInput.sendKeys(simNumber);
  }

  async getSimNumberInput() {
    return this.simNumberInput.getAttribute('value');
  }

  async setUserStartedUsingFromInput(userStartedUsingFrom) {
    await this.userStartedUsingFromInput.sendKeys(userStartedUsingFrom);
  }

  async getUserStartedUsingFromInput() {
    return this.userStartedUsingFromInput.getAttribute('value');
  }

  async setDeviceOnLocationFromInput(deviceOnLocationFrom) {
    await this.deviceOnLocationFromInput.sendKeys(deviceOnLocationFrom);
  }

  async getDeviceOnLocationFromInput() {
    return this.deviceOnLocationFromInput.getAttribute('value');
  }

  async setOperatingSystemInput(operatingSystem) {
    await this.operatingSystemInput.sendKeys(operatingSystem);
  }

  async getOperatingSystemInput() {
    return this.operatingSystemInput.getAttribute('value');
  }

  async setNoteInput(note) {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput() {
    return this.noteInput.getAttribute('value');
  }

  async setOwnerEntityIdInput(ownerEntityId) {
    await this.ownerEntityIdInput.sendKeys(ownerEntityId);
  }

  async getOwnerEntityIdInput() {
    return this.ownerEntityIdInput.getAttribute('value');
  }

  async setOwnerEntityNameInput(ownerEntityName) {
    await this.ownerEntityNameInput.sendKeys(ownerEntityName);
  }

  async getOwnerEntityNameInput() {
    return this.ownerEntityNameInput.getAttribute('value');
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

  async employeeSelectLastOption() {
    await this.employeeSelect.all(by.tagName('option')).last().click();
  }

  async employeeSelectOption(option) {
    await this.employeeSelect.sendKeys(option);
  }

  getEmployeeSelect() {
    return this.employeeSelect;
  }

  async getEmployeeSelectedOption() {
    return this.employeeSelect.element(by.css('option:checked')).getText();
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
    await this.setDeviceNameInput('deviceName');
    expect(await this.getDeviceNameInput()).to.match(/deviceName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDeviceModelInput('deviceModel');
    expect(await this.getDeviceModelInput()).to.match(/deviceModel/);
    await waitUntilDisplayed(this.saveButton);
    await this.setRegisteredDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getRegisteredDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setImeiInput('imei');
    expect(await this.getImeiInput()).to.match(/imei/);
    await waitUntilDisplayed(this.saveButton);
    await this.setSimNumberInput('simNumber');
    expect(await this.getSimNumberInput()).to.match(/simNumber/);
    await waitUntilDisplayed(this.saveButton);
    await this.setUserStartedUsingFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getUserStartedUsingFromInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setDeviceOnLocationFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getDeviceOnLocationFromInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setOperatingSystemInput('operatingSystem');
    expect(await this.getOperatingSystemInput()).to.match(/operatingSystem/);
    await waitUntilDisplayed(this.saveButton);
    await this.setNoteInput('note');
    expect(await this.getNoteInput()).to.match(/note/);
    await waitUntilDisplayed(this.saveButton);
    await this.setOwnerEntityIdInput('5');
    expect(await this.getOwnerEntityIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setOwnerEntityNameInput('ownerEntityName');
    expect(await this.getOwnerEntityNameInput()).to.match(/ownerEntityName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.employeeSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
