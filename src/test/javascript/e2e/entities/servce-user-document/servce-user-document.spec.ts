import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServceUserDocumentComponentsPage from './servce-user-document.page-object';
import ServceUserDocumentUpdatePage from './servce-user-document-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('ServceUserDocument e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let servceUserDocumentComponentsPage: ServceUserDocumentComponentsPage;
  let servceUserDocumentUpdatePage: ServceUserDocumentUpdatePage;

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
    servceUserDocumentComponentsPage = new ServceUserDocumentComponentsPage();
    servceUserDocumentComponentsPage = await servceUserDocumentComponentsPage.goToPage(navBarPage);
  });

  it('should load ServceUserDocuments', async () => {
    expect(await servceUserDocumentComponentsPage.title.getText()).to.match(/Servce User Documents/);
    expect(await servceUserDocumentComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ServceUserDocuments', async () => {
    const beforeRecordsCount = (await isVisible(servceUserDocumentComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(servceUserDocumentComponentsPage.table);
    servceUserDocumentUpdatePage = await servceUserDocumentComponentsPage.goToCreateServceUserDocument();
    await servceUserDocumentUpdatePage.enterData();

    expect(await servceUserDocumentComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(servceUserDocumentComponentsPage.table);
    await waitUntilCount(servceUserDocumentComponentsPage.records, beforeRecordsCount + 1);
    expect(await servceUserDocumentComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await servceUserDocumentComponentsPage.deleteServceUserDocument();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(servceUserDocumentComponentsPage.records, beforeRecordsCount);
      expect(await servceUserDocumentComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(servceUserDocumentComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
