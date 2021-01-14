import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ExtraDataComponentsPage from './extra-data.page-object';
import ExtraDataUpdatePage from './extra-data-update.page-object';
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

describe('ExtraData e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let extraDataComponentsPage: ExtraDataComponentsPage;
  let extraDataUpdatePage: ExtraDataUpdatePage;

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
    extraDataComponentsPage = new ExtraDataComponentsPage();
    extraDataComponentsPage = await extraDataComponentsPage.goToPage(navBarPage);
  });

  it('should load ExtraData', async () => {
    expect(await extraDataComponentsPage.title.getText()).to.match(/Extra Data/);
    expect(await extraDataComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ExtraData', async () => {
    const beforeRecordsCount = (await isVisible(extraDataComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(extraDataComponentsPage.table);
    extraDataUpdatePage = await extraDataComponentsPage.goToCreateExtraData();
    await extraDataUpdatePage.enterData();

    expect(await extraDataComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(extraDataComponentsPage.table);
    await waitUntilCount(extraDataComponentsPage.records, beforeRecordsCount + 1);
    expect(await extraDataComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await extraDataComponentsPage.deleteExtraData();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(extraDataComponentsPage.records, beforeRecordsCount);
      expect(await extraDataComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(extraDataComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
