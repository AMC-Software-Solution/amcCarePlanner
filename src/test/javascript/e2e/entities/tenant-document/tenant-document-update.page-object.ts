import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

import path from 'path';

const expect = chai.expect;

const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
const absolutePath = path.resolve(__dirname, fileToUpload);
export default class TenantDocumentUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.tenantDocument.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  documentNameInput: ElementFinder = element(by.css('input#tenant-document-documentName'));
  documentCodeInput: ElementFinder = element(by.css('input#tenant-document-documentCode'));
  documentNumberInput: ElementFinder = element(by.css('input#tenant-document-documentNumber'));
  documentTypeSelect: ElementFinder = element(by.css('select#tenant-document-documentType'));
  documentStatusSelect: ElementFinder = element(by.css('select#tenant-document-documentStatus'));
  noteInput: ElementFinder = element(by.css('input#tenant-document-note'));
  issuedDateInput: ElementFinder = element(by.css('input#tenant-document-issuedDate'));
  expiryDateInput: ElementFinder = element(by.css('input#tenant-document-expiryDate'));
  uploadedDateInput: ElementFinder = element(by.css('input#tenant-document-uploadedDate'));
  documentFileInput: ElementFinder = element(by.css('input#file_documentFile'));
  documentFileUrlInput: ElementFinder = element(by.css('input#tenant-document-documentFileUrl'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#tenant-document-lastUpdatedDate'));
  tenantIdInput: ElementFinder = element(by.css('input#tenant-document-tenantId'));
  uploadedBySelect: ElementFinder = element(by.css('select#tenant-document-uploadedBy'));
  approvedBySelect: ElementFinder = element(by.css('select#tenant-document-approvedBy'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setDocumentNameInput(documentName) {
    await this.documentNameInput.sendKeys(documentName);
  }

  async getDocumentNameInput() {
    return this.documentNameInput.getAttribute('value');
  }

  async setDocumentCodeInput(documentCode) {
    await this.documentCodeInput.sendKeys(documentCode);
  }

  async getDocumentCodeInput() {
    return this.documentCodeInput.getAttribute('value');
  }

  async setDocumentNumberInput(documentNumber) {
    await this.documentNumberInput.sendKeys(documentNumber);
  }

  async getDocumentNumberInput() {
    return this.documentNumberInput.getAttribute('value');
  }

  async setDocumentTypeSelect(documentType) {
    await this.documentTypeSelect.sendKeys(documentType);
  }

  async getDocumentTypeSelect() {
    return this.documentTypeSelect.element(by.css('option:checked')).getText();
  }

  async documentTypeSelectLastOption() {
    await this.documentTypeSelect.all(by.tagName('option')).last().click();
  }
  async setDocumentStatusSelect(documentStatus) {
    await this.documentStatusSelect.sendKeys(documentStatus);
  }

  async getDocumentStatusSelect() {
    return this.documentStatusSelect.element(by.css('option:checked')).getText();
  }

  async documentStatusSelectLastOption() {
    await this.documentStatusSelect.all(by.tagName('option')).last().click();
  }
  async setNoteInput(note) {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput() {
    return this.noteInput.getAttribute('value');
  }

  async setIssuedDateInput(issuedDate) {
    await this.issuedDateInput.sendKeys(issuedDate);
  }

  async getIssuedDateInput() {
    return this.issuedDateInput.getAttribute('value');
  }

  async setExpiryDateInput(expiryDate) {
    await this.expiryDateInput.sendKeys(expiryDate);
  }

  async getExpiryDateInput() {
    return this.expiryDateInput.getAttribute('value');
  }

  async setUploadedDateInput(uploadedDate) {
    await this.uploadedDateInput.sendKeys(uploadedDate);
  }

  async getUploadedDateInput() {
    return this.uploadedDateInput.getAttribute('value');
  }

  async setDocumentFileInput(documentFile) {
    await this.documentFileInput.sendKeys(documentFile);
  }

  async getDocumentFileInput() {
    return this.documentFileInput.getAttribute('value');
  }

  async setDocumentFileUrlInput(documentFileUrl) {
    await this.documentFileUrlInput.sendKeys(documentFileUrl);
  }

  async getDocumentFileUrlInput() {
    return this.documentFileUrlInput.getAttribute('value');
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

  async uploadedBySelectLastOption() {
    await this.uploadedBySelect.all(by.tagName('option')).last().click();
  }

  async uploadedBySelectOption(option) {
    await this.uploadedBySelect.sendKeys(option);
  }

  getUploadedBySelect() {
    return this.uploadedBySelect;
  }

  async getUploadedBySelectedOption() {
    return this.uploadedBySelect.element(by.css('option:checked')).getText();
  }

  async approvedBySelectLastOption() {
    await this.approvedBySelect.all(by.tagName('option')).last().click();
  }

  async approvedBySelectOption(option) {
    await this.approvedBySelect.sendKeys(option);
  }

  getApprovedBySelect() {
    return this.approvedBySelect;
  }

  async getApprovedBySelectedOption() {
    return this.approvedBySelect.element(by.css('option:checked')).getText();
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
    await this.setDocumentNameInput('documentName');
    expect(await this.getDocumentNameInput()).to.match(/documentName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDocumentCodeInput('documentCode');
    expect(await this.getDocumentCodeInput()).to.match(/documentCode/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDocumentNumberInput('documentNumber');
    expect(await this.getDocumentNumberInput()).to.match(/documentNumber/);
    await waitUntilDisplayed(this.saveButton);
    await this.documentTypeSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.documentStatusSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setNoteInput('note');
    expect(await this.getNoteInput()).to.match(/note/);
    await waitUntilDisplayed(this.saveButton);
    await this.setIssuedDateInput('01-01-2001');
    expect(await this.getIssuedDateInput()).to.eq('2001-01-01');
    await waitUntilDisplayed(this.saveButton);
    await this.setExpiryDateInput('01-01-2001');
    expect(await this.getExpiryDateInput()).to.eq('2001-01-01');
    await waitUntilDisplayed(this.saveButton);
    await this.setUploadedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getUploadedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setDocumentFileInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.setDocumentFileUrlInput('documentFileUrl');
    expect(await this.getDocumentFileUrlInput()).to.match(/documentFileUrl/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantIdInput('5');
    expect(await this.getTenantIdInput()).to.eq('5');
    await this.uploadedBySelectLastOption();
    await this.approvedBySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
