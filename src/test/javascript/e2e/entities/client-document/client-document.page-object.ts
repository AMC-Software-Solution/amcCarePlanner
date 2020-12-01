import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ClientDocumentUpdatePage from './client-document-update.page-object';

const expect = chai.expect;
export class ClientDocumentDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.clientDocument.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-clientDocument'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ClientDocumentComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('client-document-heading'));
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
    await navBarPage.getEntityPage('client-document');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateClientDocument() {
    await this.createButton.click();
    return new ClientDocumentUpdatePage();
  }

  async deleteClientDocument() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const clientDocumentDeleteDialog = new ClientDocumentDeleteDialog();
    await waitUntilDisplayed(clientDocumentDeleteDialog.deleteModal);
    expect(await clientDocumentDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.clientDocument.delete.question/);
    await clientDocumentDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(clientDocumentDeleteDialog.deleteModal);

    expect(await isVisible(clientDocumentDeleteDialog.deleteModal)).to.be.false;
  }
}
