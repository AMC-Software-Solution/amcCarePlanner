import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServiceUserComponentsPage from './service-user.page-object';
import ServiceUserUpdatePage from './service-user-update.page-object';
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

describe('ServiceUser e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceUserComponentsPage: ServiceUserComponentsPage;
  let serviceUserUpdatePage: ServiceUserUpdatePage;

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
    serviceUserComponentsPage = new ServiceUserComponentsPage();
    serviceUserComponentsPage = await serviceUserComponentsPage.goToPage(navBarPage);
  });

  it('should load ServiceUsers', async () => {
    expect(await serviceUserComponentsPage.title.getText()).to.match(/Service Users/);
    expect(await serviceUserComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ServiceUsers', async () => {
    const beforeRecordsCount = (await isVisible(serviceUserComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(serviceUserComponentsPage.table);
    serviceUserUpdatePage = await serviceUserComponentsPage.goToCreateServiceUser();
    await serviceUserUpdatePage.enterData();

    expect(await serviceUserComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(serviceUserComponentsPage.table);
    await waitUntilCount(serviceUserComponentsPage.records, beforeRecordsCount + 1);
    expect(await serviceUserComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await serviceUserComponentsPage.deleteServiceUser();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(serviceUserComponentsPage.records, beforeRecordsCount);
      expect(await serviceUserComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(serviceUserComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
