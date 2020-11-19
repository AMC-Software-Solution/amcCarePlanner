import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TenantComponentsPage from './tenant.page-object';
import TenantUpdatePage from './tenant-update.page-object';
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

describe('Tenant e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tenantComponentsPage: TenantComponentsPage;
  let tenantUpdatePage: TenantUpdatePage;

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
    tenantComponentsPage = new TenantComponentsPage();
    tenantComponentsPage = await tenantComponentsPage.goToPage(navBarPage);
  });

  it('should load Tenants', async () => {
    expect(await tenantComponentsPage.title.getText()).to.match(/Tenants/);
    expect(await tenantComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Tenants', async () => {
    const beforeRecordsCount = (await isVisible(tenantComponentsPage.noRecords)) ? 0 : await getRecordsCount(tenantComponentsPage.table);
    tenantUpdatePage = await tenantComponentsPage.goToCreateTenant();
    await tenantUpdatePage.enterData();

    expect(await tenantComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(tenantComponentsPage.table);
    await waitUntilCount(tenantComponentsPage.records, beforeRecordsCount + 1);
    expect(await tenantComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await tenantComponentsPage.deleteTenant();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(tenantComponentsPage.records, beforeRecordsCount);
      expect(await tenantComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(tenantComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
