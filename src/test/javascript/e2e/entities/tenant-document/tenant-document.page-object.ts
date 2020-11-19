import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import TenantDocumentUpdatePage from './tenant-document-update.page-object';

const expect = chai.expect;
export class TenantDocumentDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.tenantDocument.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-tenantDocument'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class TenantDocumentComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('tenant-document-heading'));
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
    await navBarPage.getEntityPage('tenant-document');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateTenantDocument() {
    await this.createButton.click();
    return new TenantDocumentUpdatePage();
  }

  async deleteTenantDocument() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const tenantDocumentDeleteDialog = new TenantDocumentDeleteDialog();
    await waitUntilDisplayed(tenantDocumentDeleteDialog.deleteModal);
    expect(await tenantDocumentDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.tenantDocument.delete.question/);
    await tenantDocumentDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(tenantDocumentDeleteDialog.deleteModal);

    expect(await isVisible(tenantDocumentDeleteDialog.deleteModal)).to.be.false;
  }
}
