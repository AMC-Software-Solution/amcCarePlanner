import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

import path from 'path';

const expect = chai.expect;

const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
const absolutePath = path.resolve(__dirname, fileToUpload);
export default class ConsentUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.consent.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  titleInput: ElementFinder = element(by.css('input#consent-title'));
  descriptionInput: ElementFinder = element(by.css('input#consent-description'));
  giveConsentInput: ElementFinder = element(by.css('input#consent-giveConsent'));
  arrangementsInput: ElementFinder = element(by.css('input#consent-arrangements'));
  serviceUserSignatureInput: ElementFinder = element(by.css('input#consent-serviceUserSignature'));
  signatureImageInput: ElementFinder = element(by.css('input#file_signatureImage'));
  signatureImageUrlInput: ElementFinder = element(by.css('input#consent-signatureImageUrl'));
  consentDateInput: ElementFinder = element(by.css('input#consent-consentDate'));
  createdDateInput: ElementFinder = element(by.css('input#consent-createdDate'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#consent-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#consent-clientId'));
  hasExtraDataInput: ElementFinder = element(by.css('input#consent-hasExtraData'));
  serviceUserSelect: ElementFinder = element(by.css('select#consent-serviceUser'));
  witnessedBySelect: ElementFinder = element(by.css('select#consent-witnessedBy'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTitleInput(title) {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput() {
    return this.titleInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  getGiveConsentInput() {
    return this.giveConsentInput;
  }
  async setArrangementsInput(arrangements) {
    await this.arrangementsInput.sendKeys(arrangements);
  }

  async getArrangementsInput() {
    return this.arrangementsInput.getAttribute('value');
  }

  async setServiceUserSignatureInput(serviceUserSignature) {
    await this.serviceUserSignatureInput.sendKeys(serviceUserSignature);
  }

  async getServiceUserSignatureInput() {
    return this.serviceUserSignatureInput.getAttribute('value');
  }

  async setSignatureImageInput(signatureImage) {
    await this.signatureImageInput.sendKeys(signatureImage);
  }

  async getSignatureImageInput() {
    return this.signatureImageInput.getAttribute('value');
  }

  async setSignatureImageUrlInput(signatureImageUrl) {
    await this.signatureImageUrlInput.sendKeys(signatureImageUrl);
  }

  async getSignatureImageUrlInput() {
    return this.signatureImageUrlInput.getAttribute('value');
  }

  async setConsentDateInput(consentDate) {
    await this.consentDateInput.sendKeys(consentDate);
  }

  async getConsentDateInput() {
    return this.consentDateInput.getAttribute('value');
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

  async setClientIdInput(clientId) {
    await this.clientIdInput.sendKeys(clientId);
  }

  async getClientIdInput() {
    return this.clientIdInput.getAttribute('value');
  }

  getHasExtraDataInput() {
    return this.hasExtraDataInput;
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

  async witnessedBySelectLastOption() {
    await this.witnessedBySelect.all(by.tagName('option')).last().click();
  }

  async witnessedBySelectOption(option) {
    await this.witnessedBySelect.sendKeys(option);
  }

  getWitnessedBySelect() {
    return this.witnessedBySelect;
  }

  async getWitnessedBySelectedOption() {
    return this.witnessedBySelect.element(by.css('option:checked')).getText();
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
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
    await waitUntilDisplayed(this.saveButton);
    const selectedGiveConsent = await this.getGiveConsentInput().isSelected();
    if (selectedGiveConsent) {
      await this.getGiveConsentInput().click();
      expect(await this.getGiveConsentInput().isSelected()).to.be.false;
    } else {
      await this.getGiveConsentInput().click();
      expect(await this.getGiveConsentInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setArrangementsInput('arrangements');
    expect(await this.getArrangementsInput()).to.match(/arrangements/);
    await waitUntilDisplayed(this.saveButton);
    await this.setServiceUserSignatureInput('serviceUserSignature');
    expect(await this.getServiceUserSignatureInput()).to.match(/serviceUserSignature/);
    await waitUntilDisplayed(this.saveButton);
    await this.setSignatureImageInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.setSignatureImageUrlInput('signatureImageUrl');
    expect(await this.getSignatureImageUrlInput()).to.match(/signatureImageUrl/);
    await waitUntilDisplayed(this.saveButton);
    await this.setConsentDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getConsentDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getCreatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    const selectedHasExtraData = await this.getHasExtraDataInput().isSelected();
    if (selectedHasExtraData) {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.false;
    } else {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.true;
    }
    await this.serviceUserSelectLastOption();
    await this.witnessedBySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
