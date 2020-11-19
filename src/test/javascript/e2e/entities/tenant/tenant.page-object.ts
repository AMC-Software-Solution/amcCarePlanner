import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import TenantUpdatePage from './tenant-update.page-object';

const expect = chai.expect;
export class TenantDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.tenant.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-tenant'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class TenantComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('tenant-heading'));
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
    await navBarPage.getEntityPage('tenant');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateTenant() {
    await this.createButton.click();
    return new TenantUpdatePage();
  }

  async deleteTenant() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const tenantDeleteDialog = new TenantDeleteDialog();
    await waitUntilDisplayed(tenantDeleteDialog.deleteModal);
    expect(await tenantDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.tenant.delete.question/);
    await tenantDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(tenantDeleteDialog.deleteModal);

    expect(await isVisible(tenantDeleteDialog.deleteModal)).to.be.false;
  }
}
