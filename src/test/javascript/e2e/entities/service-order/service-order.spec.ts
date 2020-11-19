import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServiceOrderComponentsPage from './service-order.page-object';
import ServiceOrderUpdatePage from './service-order-update.page-object';
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

describe('ServiceOrder e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceOrderComponentsPage: ServiceOrderComponentsPage;
  let serviceOrderUpdatePage: ServiceOrderUpdatePage;

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
    serviceOrderComponentsPage = new ServiceOrderComponentsPage();
    serviceOrderComponentsPage = await serviceOrderComponentsPage.goToPage(navBarPage);
  });

  it('should load ServiceOrders', async () => {
    expect(await serviceOrderComponentsPage.title.getText()).to.match(/Service Orders/);
    expect(await serviceOrderComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ServiceOrders', async () => {
    const beforeRecordsCount = (await isVisible(serviceOrderComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(serviceOrderComponentsPage.table);
    serviceOrderUpdatePage = await serviceOrderComponentsPage.goToCreateServiceOrder();
    await serviceOrderUpdatePage.enterData();

    expect(await serviceOrderComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(serviceOrderComponentsPage.table);
    await waitUntilCount(serviceOrderComponentsPage.records, beforeRecordsCount + 1);
    expect(await serviceOrderComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await serviceOrderComponentsPage.deleteServiceOrder();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(serviceOrderComponentsPage.records, beforeRecordsCount);
      expect(await serviceOrderComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(serviceOrderComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
