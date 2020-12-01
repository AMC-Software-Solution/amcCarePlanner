import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class DocumentTypeUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.documentType.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  documentTypeTitleInput: ElementFinder = element(by.css('input#document-type-documentTypeTitle'));
  documentTypeDescriptionInput: ElementFinder = element(by.css('input#document-type-documentTypeDescription'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#document-type-lastUpdatedDate'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setDocumentTypeTitleInput(documentTypeTitle) {
    await this.documentTypeTitleInput.sendKeys(documentTypeTitle);
  }

  async getDocumentTypeTitleInput() {
    return this.documentTypeTitleInput.getAttribute('value');
  }

  async setDocumentTypeDescriptionInput(documentTypeDescription) {
    await this.documentTypeDescriptionInput.sendKeys(documentTypeDescription);
  }

  async getDocumentTypeDescriptionInput() {
    return this.documentTypeDescriptionInput.getAttribute('value');
  }

  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
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
    await this.setDocumentTypeTitleInput('documentTypeTitle');
    expect(await this.getDocumentTypeTitleInput()).to.match(/documentTypeTitle/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDocumentTypeDescriptionInput('documentTypeDescription');
    expect(await this.getDocumentTypeDescriptionInput()).to.match(/documentTypeDescription/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
