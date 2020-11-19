import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

import path from 'path';

const expect = chai.expect;

const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
const absolutePath = path.resolve(__dirname, fileToUpload);
export default class CommunicationUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.communication.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  communicationTypeSelect: ElementFinder = element(by.css('select#communication-communicationType'));
  noteInput: ElementFinder = element(by.css('input#communication-note'));
  communicationDateInput: ElementFinder = element(by.css('input#communication-communicationDate'));
  attachmentInput: ElementFinder = element(by.css('input#file_attachment'));
  attachmentUrlInput: ElementFinder = element(by.css('input#communication-attachmentUrl'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#communication-lastUpdatedDate'));
  tenantIdInput: ElementFinder = element(by.css('input#communication-tenantId'));
  serviceUserSelect: ElementFinder = element(by.css('select#communication-serviceUser'));
  communicatedBySelect: ElementFinder = element(by.css('select#communication-communicatedBy'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setCommunicationTypeSelect(communicationType) {
    await this.communicationTypeSelect.sendKeys(communicationType);
  }

  async getCommunicationTypeSelect() {
    return this.communicationTypeSelect.element(by.css('option:checked')).getText();
  }

  async communicationTypeSelectLastOption() {
    await this.communicationTypeSelect.all(by.tagName('option')).last().click();
  }
  async setNoteInput(note) {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput() {
    return this.noteInput.getAttribute('value');
  }

  async setCommunicationDateInput(communicationDate) {
    await this.communicationDateInput.sendKeys(communicationDate);
  }

  async getCommunicationDateInput() {
    return this.communicationDateInput.getAttribute('value');
  }

  async setAttachmentInput(attachment) {
    await this.attachmentInput.sendKeys(attachment);
  }

  async getAttachmentInput() {
    return this.attachmentInput.getAttribute('value');
  }

  async setAttachmentUrlInput(attachmentUrl) {
    await this.attachmentUrlInput.sendKeys(attachmentUrl);
  }

  async getAttachmentUrlInput() {
    return this.attachmentUrlInput.getAttribute('value');
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

  async communicatedBySelectLastOption() {
    await this.communicatedBySelect.all(by.tagName('option')).last().click();
  }

  async communicatedBySelectOption(option) {
    await this.communicatedBySelect.sendKeys(option);
  }

  getCommunicatedBySelect() {
    return this.communicatedBySelect;
  }

  async getCommunicatedBySelectedOption() {
    return this.communicatedBySelect.element(by.css('option:checked')).getText();
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
    await this.communicationTypeSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setNoteInput('note');
    expect(await this.getNoteInput()).to.match(/note/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCommunicationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getCommunicationDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setAttachmentInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.setAttachmentUrlInput('attachmentUrl');
    expect(await this.getAttachmentUrlInput()).to.match(/attachmentUrl/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantIdInput('5');
    expect(await this.getTenantIdInput()).to.eq('5');
    await this.serviceUserSelectLastOption();
    await this.communicatedBySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
