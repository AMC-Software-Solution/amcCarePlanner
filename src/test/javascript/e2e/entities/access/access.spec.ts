import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import AccessComponentsPage from './access.page-object';
import AccessUpdatePage from './access-update.page-object';
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

describe('Access e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let accessComponentsPage: AccessComponentsPage;
  let accessUpdatePage: AccessUpdatePage;

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
    accessComponentsPage = new AccessComponentsPage();
    accessComponentsPage = await accessComponentsPage.goToPage(navBarPage);
  });

  it('should load Accesses', async () => {
    expect(await accessComponentsPage.title.getText()).to.match(/Accesses/);
    expect(await accessComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Accesses', async () => {
    const beforeRecordsCount = (await isVisible(accessComponentsPage.noRecords)) ? 0 : await getRecordsCount(accessComponentsPage.table);
    accessUpdatePage = await accessComponentsPage.goToCreateAccess();
    await accessUpdatePage.enterData();

    expect(await accessComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(accessComponentsPage.table);
    await waitUntilCount(accessComponentsPage.records, beforeRecordsCount + 1);
    expect(await accessComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await accessComponentsPage.deleteAccess();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(accessComponentsPage.records, beforeRecordsCount);
      expect(await accessComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(accessComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
