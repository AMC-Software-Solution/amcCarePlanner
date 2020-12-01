import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import QuestionComponentsPage from './question.page-object';
import QuestionUpdatePage from './question-update.page-object';
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

describe('Question e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let questionComponentsPage: QuestionComponentsPage;
  let questionUpdatePage: QuestionUpdatePage;

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
    questionComponentsPage = new QuestionComponentsPage();
    questionComponentsPage = await questionComponentsPage.goToPage(navBarPage);
  });

  it('should load Questions', async () => {
    expect(await questionComponentsPage.title.getText()).to.match(/Questions/);
    expect(await questionComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Questions', async () => {
    const beforeRecordsCount = (await isVisible(questionComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(questionComponentsPage.table);
    questionUpdatePage = await questionComponentsPage.goToCreateQuestion();
    await questionUpdatePage.enterData();

    expect(await questionComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(questionComponentsPage.table);
    await waitUntilCount(questionComponentsPage.records, beforeRecordsCount + 1);
    expect(await questionComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await questionComponentsPage.deleteQuestion();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(questionComponentsPage.records, beforeRecordsCount);
      expect(await questionComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(questionComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
