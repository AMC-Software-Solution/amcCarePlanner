import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import SystemEventsHistoryUpdatePage from './system-events-history-update.page-object';

const expect = chai.expect;
export class SystemEventsHistoryDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.systemEventsHistory.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-systemEventsHistory'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class SystemEventsHistoryComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('system-events-history-heading'));
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
    await navBarPage.getEntityPage('system-events-history');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateSystemEventsHistory() {
    await this.createButton.click();
    return new SystemEventsHistoryUpdatePage();
  }

  async deleteSystemEventsHistory() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const systemEventsHistoryDeleteDialog = new SystemEventsHistoryDeleteDialog();
    await waitUntilDisplayed(systemEventsHistoryDeleteDialog.deleteModal);
    expect(await systemEventsHistoryDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /carePlannerApp.systemEventsHistory.delete.question/
    );
    await systemEventsHistoryDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(systemEventsHistoryDeleteDialog.deleteModal);

    expect(await isVisible(systemEventsHistoryDeleteDialog.deleteModal)).to.be.false;
  }
}
