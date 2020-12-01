import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ServceUserDocumentUpdatePage from './servce-user-document-update.page-object';

const expect = chai.expect;
export class ServceUserDocumentDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.servceUserDocument.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-servceUserDocument'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ServceUserDocumentComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('servce-user-document-heading'));
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
    await navBarPage.getEntityPage('servce-user-document');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateServceUserDocument() {
    await this.createButton.click();
    return new ServceUserDocumentUpdatePage();
  }

  async deleteServceUserDocument() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const servceUserDocumentDeleteDialog = new ServceUserDocumentDeleteDialog();
    await waitUntilDisplayed(servceUserDocumentDeleteDialog.deleteModal);
    expect(await servceUserDocumentDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /carePlannerApp.servceUserDocument.delete.question/
    );
    await servceUserDocumentDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(servceUserDocumentDeleteDialog.deleteModal);

    expect(await isVisible(servceUserDocumentDeleteDialog.deleteModal)).to.be.false;
  }
}
