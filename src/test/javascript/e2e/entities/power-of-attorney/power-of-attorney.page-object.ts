import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import PowerOfAttorneyUpdatePage from './power-of-attorney-update.page-object';

const expect = chai.expect;
export class PowerOfAttorneyDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.powerOfAttorney.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-powerOfAttorney'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class PowerOfAttorneyComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('power-of-attorney-heading'));
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
    await navBarPage.getEntityPage('power-of-attorney');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreatePowerOfAttorney() {
    await this.createButton.click();
    return new PowerOfAttorneyUpdatePage();
  }

  async deletePowerOfAttorney() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const powerOfAttorneyDeleteDialog = new PowerOfAttorneyDeleteDialog();
    await waitUntilDisplayed(powerOfAttorneyDeleteDialog.deleteModal);
    expect(await powerOfAttorneyDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /carePlannerApp.powerOfAttorney.delete.question/
    );
    await powerOfAttorneyDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(powerOfAttorneyDeleteDialog.deleteModal);

    expect(await isVisible(powerOfAttorneyDeleteDialog.deleteModal)).to.be.false;
  }
}
