import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import QuestionTypeUpdatePage from './question-type-update.page-object';

const expect = chai.expect;
export class QuestionTypeDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('carePlannerApp.questionType.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-questionType'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class QuestionTypeComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('question-type-heading'));
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
    await navBarPage.getEntityPage('question-type');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateQuestionType() {
    await this.createButton.click();
    return new QuestionTypeUpdatePage();
  }

  async deleteQuestionType() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const questionTypeDeleteDialog = new QuestionTypeDeleteDialog();
    await waitUntilDisplayed(questionTypeDeleteDialog.deleteModal);
    expect(await questionTypeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/carePlannerApp.questionType.delete.question/);
    await questionTypeDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(questionTypeDeleteDialog.deleteModal);

    expect(await isVisible(questionTypeDeleteDialog.deleteModal)).to.be.false;
  }
}
