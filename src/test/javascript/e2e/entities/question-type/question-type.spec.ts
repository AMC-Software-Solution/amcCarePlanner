import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import QuestionTypeComponentsPage from './question-type.page-object';
import QuestionTypeUpdatePage from './question-type-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('QuestionType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let questionTypeComponentsPage: QuestionTypeComponentsPage;
  let questionTypeUpdatePage: QuestionTypeUpdatePage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    questionTypeComponentsPage = new QuestionTypeComponentsPage();
    questionTypeComponentsPage = await questionTypeComponentsPage.goToPage(navBarPage);
  });

  it('should load QuestionTypes', async () => {
    expect(await questionTypeComponentsPage.title.getText()).to.match(/Question Types/);
    expect(await questionTypeComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete QuestionTypes', async () => {
    const beforeRecordsCount = (await isVisible(questionTypeComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(questionTypeComponentsPage.table);
    questionTypeUpdatePage = await questionTypeComponentsPage.goToCreateQuestionType();
    await questionTypeUpdatePage.enterData();

    expect(await questionTypeComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(questionTypeComponentsPage.table);
    await waitUntilCount(questionTypeComponentsPage.records, beforeRecordsCount + 1);
    expect(await questionTypeComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await questionTypeComponentsPage.deleteQuestionType();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(questionTypeComponentsPage.records, beforeRecordsCount);
      expect(await questionTypeComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(questionTypeComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
