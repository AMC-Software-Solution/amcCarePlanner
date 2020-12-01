import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class AccessUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.access.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  keySafeNumberInput: ElementFinder = element(by.css('input#access-keySafeNumber'));
  accessDetailsInput: ElementFinder = element(by.css('input#access-accessDetails'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#access-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#access-clientId'));
  serviceUserSelect: ElementFinder = element(by.css('select#access-serviceUser'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setKeySafeNumberInput(keySafeNumber) {
    await this.keySafeNumberInput.sendKeys(keySafeNumber);
  }

  async getKeySafeNumberInput() {
    return this.keySafeNumberInput.getAttribute('value');
  }

  async setAccessDetailsInput(accessDetails) {
    await this.accessDetailsInput.sendKeys(accessDetails);
  }

  async getAccessDetailsInput() {
    return this.accessDetailsInput.getAttribute('value');
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
    await this.setKeySafeNumberInput('keySafeNumber');
    expect(await this.getKeySafeNumberInput()).to.match(/keySafeNumber/);
    await waitUntilDisplayed(this.saveButton);
    await this.setAccessDetailsInput('accessDetails');
    expect(await this.getAccessDetailsInput()).to.match(/accessDetails/);
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
