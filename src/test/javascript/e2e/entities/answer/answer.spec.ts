import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import AnswerComponentsPage from './answer.page-object';
import AnswerUpdatePage from './answer-update.page-object';
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

describe('Answer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let answerComponentsPage: AnswerComponentsPage;
  let answerUpdatePage: AnswerUpdatePage;

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
    answerComponentsPage = new AnswerComponentsPage();
    answerComponentsPage = await answerComponentsPage.goToPage(navBarPage);
  });

  it('should load Answers', async () => {
    expect(await answerComponentsPage.title.getText()).to.match(/Answers/);
    expect(await answerComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Answers', async () => {
    const beforeRecordsCount = (await isVisible(answerComponentsPage.noRecords)) ? 0 : await getRecordsCount(answerComponentsPage.table);
    answerUpdatePage = await answerComponentsPage.goToCreateAnswer();
    await answerUpdatePage.enterData();

    expect(await answerComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(answerComponentsPage.table);
    await waitUntilCount(answerComponentsPage.records, beforeRecordsCount + 1);
    expect(await answerComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await answerComponentsPage.deleteAnswer();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(answerComponentsPage.records, beforeRecordsCount);
      expect(await answerComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(answerComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
