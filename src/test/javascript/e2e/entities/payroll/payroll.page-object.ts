import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import PayrollUpdatePage from './payroll-update.page-object';

const expect = chai.expect;
export class PayrollDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.payroll.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-payroll'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class PayrollComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('payroll-heading'));
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
    await navBarPage.getEntityPage('payroll');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreatePayroll() {
    await this.createButton.click();
    return new PayrollUpdatePage();
  }

  async deletePayroll() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const payrollDeleteDialog = new PayrollDeleteDialog();
    await waitUntilDisplayed(payrollDeleteDialog.deleteModal);
    expect(await payrollDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.payroll.delete.question/);
    await payrollDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(payrollDeleteDialog.deleteModal);

    expect(await isVisible(payrollDeleteDialog.deleteModal)).to.be.false;
  }
}
