import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class CountryUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.country.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  countryNameInput: ElementFinder = element(by.css('input#country-countryName'));
  countryIsoCodeInput: ElementFinder = element(by.css('input#country-countryIsoCode'));
  countryFlagUrlInput: ElementFinder = element(by.css('input#country-countryFlagUrl'));
  countryCallingCodeInput: ElementFinder = element(by.css('input#country-countryCallingCode'));
  countryTelDigitLengthInput: ElementFinder = element(by.css('input#country-countryTelDigitLength'));
  createdDateInput: ElementFinder = element(by.css('input#country-createdDate'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#country-lastUpdatedDate'));
  hasExtraDataInput: ElementFinder = element(by.css('input#country-hasExtraData'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setCountryNameInput(countryName) {
    await this.countryNameInput.sendKeys(countryName);
  }

  async getCountryNameInput() {
    return this.countryNameInput.getAttribute('value');
  }

  async setCountryIsoCodeInput(countryIsoCode) {
    await this.countryIsoCodeInput.sendKeys(countryIsoCode);
  }

  async getCountryIsoCodeInput() {
    return this.countryIsoCodeInput.getAttribute('value');
  }

  async setCountryFlagUrlInput(countryFlagUrl) {
    await this.countryFlagUrlInput.sendKeys(countryFlagUrl);
  }

  async getCountryFlagUrlInput() {
    return this.countryFlagUrlInput.getAttribute('value');
  }

  async setCountryCallingCodeInput(countryCallingCode) {
    await this.countryCallingCodeInput.sendKeys(countryCallingCode);
  }

  async getCountryCallingCodeInput() {
    return this.countryCallingCodeInput.getAttribute('value');
  }

  async setCountryTelDigitLengthInput(countryTelDigitLength) {
    await this.countryTelDigitLengthInput.sendKeys(countryTelDigitLength);
  }

  async getCountryTelDigitLengthInput() {
    return this.countryTelDigitLengthInput.getAttribute('value');
  }

  async setCreatedDateInput(createdDate) {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput() {
    return this.createdDateInput.getAttribute('value');
  }

  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
  }

  getHasExtraDataInput() {
    return this.hasExtraDataInput;
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
    await this.setCountryNameInput('countryName');
    expect(await this.getCountryNameInput()).to.match(/countryName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCountryIsoCodeInput('countryIsoCode');
    expect(await this.getCountryIsoCodeInput()).to.match(/countryIsoCode/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCountryFlagUrlInput('countryFlagUrl');
    expect(await this.getCountryFlagUrlInput()).to.match(/countryFlagUrl/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCountryCallingCodeInput('countryCallingCode');
    expect(await this.getCountryCallingCodeInput()).to.match(/countryCallingCode/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCountryTelDigitLengthInput('5');
    expect(await this.getCountryTelDigitLengthInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getCreatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    const selectedHasExtraData = await this.getHasExtraDataInput().isSelected();
    if (selectedHasExtraData) {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.false;
    } else {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.true;
    }
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
