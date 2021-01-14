import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import EqualityUpdatePage from './equality-update.page-object';

const expect = chai.expect;
export class EqualityDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.equality.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-equality'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class EqualityComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('equality-heading'));
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
    await navBarPage.getEntityPage('equality');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateEquality() {
    await this.createButton.click();
    return new EqualityUpdatePage();
  }

  async deleteEquality() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const equalityDeleteDialog = new EqualityDeleteDialog();
    await waitUntilDisplayed(equalityDeleteDialog.deleteModal);
    expect(await equalityDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.equality.delete.question/);
    await equalityDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(equalityDeleteDialog.deleteModal);

    expect(await isVisible(equalityDeleteDialog.deleteModal)).to.be.false;
  }
}
