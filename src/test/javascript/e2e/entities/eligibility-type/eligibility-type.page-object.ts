import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import EligibilityTypeUpdatePage from './eligibility-type-update.page-object';

const expect = chai.expect;
export class EligibilityTypeDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.eligibilityType.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-eligibilityType'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class EligibilityTypeComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('eligibility-type-heading'));
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
    await navBarPage.getEntityPage('eligibility-type');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateEligibilityType() {
    await this.createButton.click();
    return new EligibilityTypeUpdatePage();
  }

  async deleteEligibilityType() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const eligibilityTypeDeleteDialog = new EligibilityTypeDeleteDialog();
    await waitUntilDisplayed(eligibilityTypeDeleteDialog.deleteModal);
    expect(await eligibilityTypeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /carePlannerApp.eligibilityType.delete.question/
    );
    await eligibilityTypeDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(eligibilityTypeDeleteDialog.deleteModal);

    expect(await isVisible(eligibilityTypeDeleteDialog.deleteModal)).to.be.false;
  }
}
