import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import AccessUpdatePage from './access-update.page-object';

const expect = chai.expect;
export class AccessDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.access.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-access'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class AccessComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('access-heading'));
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
    await navBarPage.getEntityPage('access');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateAccess() {
    await this.createButton.click();
    return new AccessUpdatePage();
  }

  async deleteAccess() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const accessDeleteDialog = new AccessDeleteDialog();
    await waitUntilDisplayed(accessDeleteDialog.deleteModal);
    expect(await accessDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.access.delete.question/);
    await accessDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(accessDeleteDialog.deleteModal);

    expect(await isVisible(accessDeleteDialog.deleteModal)).to.be.false;
  }
}
