import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ClientDocumentComponentsPage from './client-document.page-object';
import ClientDocumentUpdatePage from './client-document-update.page-object';
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

describe('ClientDocument e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let clientDocumentComponentsPage: ClientDocumentComponentsPage;
  let clientDocumentUpdatePage: ClientDocumentUpdatePage;

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
    clientDocumentComponentsPage = new ClientDocumentComponentsPage();
    clientDocumentComponentsPage = await clientDocumentComponentsPage.goToPage(navBarPage);
  });

  it('should load ClientDocuments', async () => {
    expect(await clientDocumentComponentsPage.title.getText()).to.match(/Client Documents/);
    expect(await clientDocumentComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ClientDocuments', async () => {
    const beforeRecordsCount = (await isVisible(clientDocumentComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(clientDocumentComponentsPage.table);
    clientDocumentUpdatePage = await clientDocumentComponentsPage.goToCreateClientDocument();
    await clientDocumentUpdatePage.enterData();

    expect(await clientDocumentComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(clientDocumentComponentsPage.table);
    await waitUntilCount(clientDocumentComponentsPage.records, beforeRecordsCount + 1);
    expect(await clientDocumentComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await clientDocumentComponentsPage.deleteClientDocument();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(clientDocumentComponentsPage.records, beforeRecordsCount);
      expect(await clientDocumentComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(clientDocumentComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
