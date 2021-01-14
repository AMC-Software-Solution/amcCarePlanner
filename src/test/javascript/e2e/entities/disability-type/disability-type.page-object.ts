import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import DisabilityTypeUpdatePage from './disability-type-update.page-object';

const expect = chai.expect;
export class DisabilityTypeDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.disabilityType.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-disabilityType'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class DisabilityTypeComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('disability-type-heading'));
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
    await navBarPage.getEntityPage('disability-type');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateDisabilityType() {
    await this.createButton.click();
    return new DisabilityTypeUpdatePage();
  }

  async deleteDisabilityType() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const disabilityTypeDeleteDialog = new DisabilityTypeDeleteDialog();
    await waitUntilDisplayed(disabilityTypeDeleteDialog.deleteModal);
    expect(await disabilityTypeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.disabilityType.delete.question/);
    await disabilityTypeDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(disabilityTypeDeleteDialog.deleteModal);

    expect(await isVisible(disabilityTypeDeleteDialog.deleteModal)).to.be.false;
  }
}
