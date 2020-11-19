import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ConsentComponentsPage from './consent.page-object';
import ConsentUpdatePage from './consent-update.page-object';
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

describe('Consent e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let consentComponentsPage: ConsentComponentsPage;
  let consentUpdatePage: ConsentUpdatePage;

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
    consentComponentsPage = new ConsentComponentsPage();
    consentComponentsPage = await consentComponentsPage.goToPage(navBarPage);
  });

  it('should load Consents', async () => {
    expect(await consentComponentsPage.title.getText()).to.match(/Consents/);
    expect(await consentComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Consents', async () => {
    const beforeRecordsCount = (await isVisible(consentComponentsPage.noRecords)) ? 0 : await getRecordsCount(consentComponentsPage.table);
    consentUpdatePage = await consentComponentsPage.goToCreateConsent();
    await consentUpdatePage.enterData();

    expect(await consentComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(consentComponentsPage.table);
    await waitUntilCount(consentComponentsPage.records, beforeRecordsCount + 1);
    expect(await consentComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await consentComponentsPage.deleteConsent();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(consentComponentsPage.records, beforeRecordsCount);
      expect(await consentComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(consentComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
