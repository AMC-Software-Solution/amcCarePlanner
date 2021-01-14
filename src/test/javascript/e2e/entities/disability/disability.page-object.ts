import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import DisabilityUpdatePage from './disability-update.page-object';

const expect = chai.expect;
export class DisabilityDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.disability.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-disability'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class DisabilityComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('disability-heading'));
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
    await navBarPage.getEntityPage('disability');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateDisability() {
    await this.createButton.click();
    return new DisabilityUpdatePage();
  }

  async deleteDisability() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const disabilityDeleteDialog = new DisabilityDeleteDialog();
    await waitUntilDisplayed(disabilityDeleteDialog.deleteModal);
    expect(await disabilityDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.disability.delete.question/);
    await disabilityDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(disabilityDeleteDialog.deleteModal);

    expect(await isVisible(disabilityDeleteDialog.deleteModal)).to.be.false;
  }
}
