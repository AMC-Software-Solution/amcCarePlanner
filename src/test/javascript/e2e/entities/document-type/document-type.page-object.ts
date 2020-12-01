import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import DocumentTypeUpdatePage from './document-type-update.page-object';

const expect = chai.expect;
export class DocumentTypeDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.documentType.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-documentType'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class DocumentTypeComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('document-type-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('document-type');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateDocumentType() {
    await this.createButton.click();
    return new DocumentTypeUpdatePage();
  }

  async deleteDocumentType() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const documentTypeDeleteDialog = new DocumentTypeDeleteDialog();
    await waitUntilDisplayed(documentTypeDeleteDialog.deleteModal);
    expect(await documentTypeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.documentType.delete.question/);
    await documentTypeDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(documentTypeDeleteDialog.deleteModal);

    expect(await isVisible(documentTypeDeleteDialog.deleteModal)).to.be.false;
  }
}
