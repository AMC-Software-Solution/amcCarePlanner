import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ServiceOrderUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.serviceOrder.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  titleInput: ElementFinder = element(by.css('input#service-order-title'));
  serviceDescriptionInput: ElementFinder = element(by.css('input#service-order-serviceDescription'));
  serviceHourlyRateInput: ElementFinder = element(by.css('input#service-order-serviceHourlyRate'));
  clientIdInput: ElementFinder = element(by.css('input#service-order-clientId'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#service-order-lastUpdatedDate'));
  currencySelect: ElementFinder = element(by.css('select#service-order-currency'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTitleInput(title) {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput() {
    return this.titleInput.getAttribute('value');
  }

  async setServiceDescriptionInput(serviceDescription) {
    await this.serviceDescriptionInput.sendKeys(serviceDescription);
  }

  async getServiceDescriptionInput() {
    return this.serviceDescriptionInput.getAttribute('value');
  }

  async setServiceHourlyRateInput(serviceHourlyRate) {
    await this.serviceHourlyRateInput.sendKeys(serviceHourlyRate);
  }

  async getServiceHourlyRateInput() {
    return this.serviceHourlyRateInput.getAttribute('value');
  }

  async setClientIdInput(clientId) {
    await this.clientIdInput.sendKeys(clientId);
  }

  async getClientIdInput() {
    return this.clientIdInput.getAttribute('value');
  }

  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
  }

  async currencySelectLastOption() {
    await this.currencySelect.all(by.tagName('option')).last().click();
  }

  async currencySelectOption(option) {
    await this.currencySelect.sendKeys(option);
  }

  getCurrencySelect() {
    return this.currencySelect;
  }

  async getCurrencySelectedOption() {
    return this.currencySelect.element(by.css('option:checked')).getText();
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
    await this.setTitleInput('title');
    expect(await this.getTitleInput()).to.match(/title/);
    await waitUntilDisplayed(this.saveButton);
    await this.setServiceDescriptionInput('serviceDescription');
    expect(await this.getServiceDescriptionInput()).to.match(/serviceDescription/);
    await waitUntilDisplayed(this.saveButton);
    await this.setServiceHourlyRateInput('5');
    expect(await this.getServiceHourlyRateInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await this.currencySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
