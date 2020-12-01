import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ServiceUserContactUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.serviceUserContact.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  addressInput: ElementFinder = element(by.css('input#service-user-contact-address'));
  cityOrTownInput: ElementFinder = element(by.css('input#service-user-contact-cityOrTown'));
  countyInput: ElementFinder = element(by.css('input#service-user-contact-county'));
  postCodeInput: ElementFinder = element(by.css('input#service-user-contact-postCode'));
  telephoneInput: ElementFinder = element(by.css('input#service-user-contact-telephone'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#service-user-contact-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#service-user-contact-clientId'));
  serviceUserSelect: ElementFinder = element(by.css('select#service-user-contact-serviceUser'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return this.addressInput.getAttribute('value');
  }

  async setCityOrTownInput(cityOrTown) {
    await this.cityOrTownInput.sendKeys(cityOrTown);
  }

  async getCityOrTownInput() {
    return this.cityOrTownInput.getAttribute('value');
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

  async setTelephoneInput(telephone) {
    await this.telephoneInput.sendKeys(telephone);
  }

  async getTelephoneInput() {
    return this.telephoneInput.getAttribute('value');
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
    await this.setAddressInput('address');
    expect(await this.getAddressInput()).to.match(/address/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCityOrTownInput('cityOrTown');
    expect(await this.getCityOrTownInput()).to.match(/cityOrTown/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCountyInput('county');
    expect(await this.getCountyInput()).to.match(/county/);
    await waitUntilDisplayed(this.saveButton);
    await this.setPostCodeInput('postCode');
    expect(await this.getPostCodeInput()).to.match(/postCode/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTelephoneInput('telephone');
    expect(await this.getTelephoneInput()).to.match(/telephone/);
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
