import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import EmployeeDesignationUpdatePage from './employee-designation-update.page-object';

const expect = chai.expect;
export class EmployeeDesignationDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.employeeDesignation.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-employeeDesignation'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class EmployeeDesignationComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('employee-designation-heading'));
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
    await navBarPage.getEntityPage('employee-designation');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateEmployeeDesignation() {
    await this.createButton.click();
    return new EmployeeDesignationUpdatePage();
  }

  async deleteEmployeeDesignation() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const employeeDesignationDeleteDialog = new EmployeeDesignationDeleteDialog();
    await waitUntilDisplayed(employeeDesignationDeleteDialog.deleteModal);
    expect(await employeeDesignationDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /carePlannerApp.employeeDesignation.delete.question/
    );
    await employeeDesignationDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(employeeDesignationDeleteDialog.deleteModal);

    expect(await isVisible(employeeDesignationDeleteDialog.deleteModal)).to.be.false;
  }
}
