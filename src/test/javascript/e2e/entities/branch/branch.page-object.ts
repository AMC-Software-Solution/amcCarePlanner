import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import BranchUpdatePage from './branch-update.page-object';

const expect = chai.expect;
export class BranchDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.branch.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-branch'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class BranchComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('branch-heading'));
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
    await navBarPage.getEntityPage('branch');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateBranch() {
    await this.createButton.click();
    return new BranchUpdatePage();
  }

  async deleteBranch() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const branchDeleteDialog = new BranchDeleteDialog();
    await waitUntilDisplayed(branchDeleteDialog.deleteModal);
    expect(await branchDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.branch.delete.question/);
    await branchDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(branchDeleteDialog.deleteModal);

    expect(await isVisible(branchDeleteDialog.deleteModal)).to.be.false;
  }
}
