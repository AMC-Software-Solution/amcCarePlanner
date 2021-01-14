import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

import path from 'path';

const expect = chai.expect;

const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
const absolutePath = path.resolve(__dirname, fileToUpload);
export default class ClientUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.client.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  clientNameInput: ElementFinder = element(by.css('input#client-clientName'));
  clientDescriptionInput: ElementFinder = element(by.css('input#client-clientDescription'));
  clientLogoInput: ElementFinder = element(by.css('input#file_clientLogo'));
  clientLogoUrlInput: ElementFinder = element(by.css('input#client-clientLogoUrl'));
  clientContactNameInput: ElementFinder = element(by.css('input#client-clientContactName'));
  clientPhoneInput: ElementFinder = element(by.css('input#client-clientPhone'));
  clientEmailInput: ElementFinder = element(by.css('input#client-clientEmail'));
  createdDateInput: ElementFinder = element(by.css('input#client-createdDate'));
  enabledInput: ElementFinder = element(by.css('input#client-enabled'));
  reasonInput: ElementFinder = element(by.css('input#client-reason'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#client-lastUpdatedDate'));
  hasExtraDataInput: ElementFinder = element(by.css('input#client-hasExtraData'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setClientNameInput(clientName) {
    await this.clientNameInput.sendKeys(clientName);
  }

  async getClientNameInput() {
    return this.clientNameInput.getAttribute('value');
  }

  async setClientDescriptionInput(clientDescription) {
    await this.clientDescriptionInput.sendKeys(clientDescription);
  }

  async getClientDescriptionInput() {
    return this.clientDescriptionInput.getAttribute('value');
  }

  async setClientLogoInput(clientLogo) {
    await this.clientLogoInput.sendKeys(clientLogo);
  }

  async getClientLogoInput() {
    return this.clientLogoInput.getAttribute('value');
  }

  async setClientLogoUrlInput(clientLogoUrl) {
    await this.clientLogoUrlInput.sendKeys(clientLogoUrl);
  }

  async getClientLogoUrlInput() {
    return this.clientLogoUrlInput.getAttribute('value');
  }

  async setClientContactNameInput(clientContactName) {
    await this.clientContactNameInput.sendKeys(clientContactName);
  }

  async getClientContactNameInput() {
    return this.clientContactNameInput.getAttribute('value');
  }

  async setClientPhoneInput(clientPhone) {
    await this.clientPhoneInput.sendKeys(clientPhone);
  }

  async getClientPhoneInput() {
    return this.clientPhoneInput.getAttribute('value');
  }

  async setClientEmailInput(clientEmail) {
    await this.clientEmailInput.sendKeys(clientEmail);
  }

  async getClientEmailInput() {
    return this.clientEmailInput.getAttribute('value');
  }

  async setCreatedDateInput(createdDate) {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput() {
    return this.createdDateInput.getAttribute('value');
  }

  getEnabledInput() {
    return this.enabledInput;
  }
  async setReasonInput(reason) {
    await this.reasonInput.sendKeys(reason);
  }

  async getReasonInput() {
    return this.reasonInput.getAttribute('value');
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
    await this.setClientNameInput('clientName');
    expect(await this.getClientNameInput()).to.match(/clientName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setClientDescriptionInput('clientDescription');
    expect(await this.getClientDescriptionInput()).to.match(/clientDescription/);
    await waitUntilDisplayed(this.saveButton);
    await this.setClientLogoInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.setClientLogoUrlInput('clientLogoUrl');
    expect(await this.getClientLogoUrlInput()).to.match(/clientLogoUrl/);
    await waitUntilDisplayed(this.saveButton);
    await this.setClientContactNameInput('clientContactName');
    expect(await this.getClientContactNameInput()).to.match(/clientContactName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setClientPhoneInput('clientPhone');
    expect(await this.getClientPhoneInput()).to.match(/clientPhone/);
    await waitUntilDisplayed(this.saveButton);
    await this.setClientEmailInput('clientEmail');
    expect(await this.getClientEmailInput()).to.match(/clientEmail/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getCreatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    const selectedEnabled = await this.getEnabledInput().isSelected();
    if (selectedEnabled) {
      await this.getEnabledInput().click();
      expect(await this.getEnabledInput().isSelected()).to.be.false;
    } else {
      await this.getEnabledInput().click();
      expect(await this.getEnabledInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setReasonInput('reason');
    expect(await this.getReasonInput()).to.match(/reason/);
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
