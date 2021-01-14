import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ExtraDataUpdatePage from './extra-data-update.page-object';

const expect = chai.expect;
export class ExtraDataDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.extraData.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-extraData'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ExtraDataComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('extra-data-heading'));
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
    await navBarPage.getEntityPage('extra-data');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateExtraData() {
    await this.createButton.click();
    return new ExtraDataUpdatePage();
  }

  async deleteExtraData() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const extraDataDeleteDialog = new ExtraDataDeleteDialog();
    await waitUntilDisplayed(extraDataDeleteDialog.deleteModal);
    expect(await extraDataDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.extraData.delete.question/);
    await extraDataDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(extraDataDeleteDialog.deleteModal);

    expect(await isVisible(extraDataDeleteDialog.deleteModal)).to.be.false;
  }
}
