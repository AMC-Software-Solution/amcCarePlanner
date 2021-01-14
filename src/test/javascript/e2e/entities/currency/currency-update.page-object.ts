import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

import path from 'path';

const expect = chai.expect;

const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
const absolutePath = path.resolve(__dirname, fileToUpload);
export default class CurrencyUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.currency.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  currencyInput: ElementFinder = element(by.css('input#currency-currency'));
  currencyIsoCodeInput: ElementFinder = element(by.css('input#currency-currencyIsoCode'));
  currencySymbolInput: ElementFinder = element(by.css('input#currency-currencySymbol'));
  currencyLogoInput: ElementFinder = element(by.css('input#file_currencyLogo'));
  currencyLogoUrlInput: ElementFinder = element(by.css('input#currency-currencyLogoUrl'));
  hasExtraDataInput: ElementFinder = element(by.css('input#currency-hasExtraData'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setCurrencyInput(currency) {
    await this.currencyInput.sendKeys(currency);
  }

  async getCurrencyInput() {
    return this.currencyInput.getAttribute('value');
  }

  async setCurrencyIsoCodeInput(currencyIsoCode) {
    await this.currencyIsoCodeInput.sendKeys(currencyIsoCode);
  }

  async getCurrencyIsoCodeInput() {
    return this.currencyIsoCodeInput.getAttribute('value');
  }

  async setCurrencySymbolInput(currencySymbol) {
    await this.currencySymbolInput.sendKeys(currencySymbol);
  }

  async getCurrencySymbolInput() {
    return this.currencySymbolInput.getAttribute('value');
  }

  async setCurrencyLogoInput(currencyLogo) {
    await this.currencyLogoInput.sendKeys(currencyLogo);
  }

  async getCurrencyLogoInput() {
    return this.currencyLogoInput.getAttribute('value');
  }

  async setCurrencyLogoUrlInput(currencyLogoUrl) {
    await this.currencyLogoUrlInput.sendKeys(currencyLogoUrl);
  }

  async getCurrencyLogoUrlInput() {
    return this.currencyLogoUrlInput.getAttribute('value');
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
    await this.setCurrencyInput('currency');
    expect(await this.getCurrencyInput()).to.match(/currency/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCurrencyIsoCodeInput('currencyIsoCode');
    expect(await this.getCurrencyIsoCodeInput()).to.match(/currencyIsoCode/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCurrencySymbolInput('currencySymbol');
    expect(await this.getCurrencySymbolInput()).to.match(/currencySymbol/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCurrencyLogoInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.setCurrencyLogoUrlInput('currencyLogoUrl');
    expect(await this.getCurrencyLogoUrlInput()).to.match(/currencyLogoUrl/);
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
