import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import SystemSettingComponentsPage from './system-setting.page-object';
import SystemSettingUpdatePage from './system-setting-update.page-object';
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

describe('SystemSetting e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let systemSettingComponentsPage: SystemSettingComponentsPage;
  let systemSettingUpdatePage: SystemSettingUpdatePage;

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
    systemSettingComponentsPage = new SystemSettingComponentsPage();
    systemSettingComponentsPage = await systemSettingComponentsPage.goToPage(navBarPage);
  });

  it('should load SystemSettings', async () => {
    expect(await systemSettingComponentsPage.title.getText()).to.match(/System Settings/);
    expect(await systemSettingComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete SystemSettings', async () => {
    const beforeRecordsCount = (await isVisible(systemSettingComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(systemSettingComponentsPage.table);
    systemSettingUpdatePage = await systemSettingComponentsPage.goToCreateSystemSetting();
    await systemSettingUpdatePage.enterData();

    expect(await systemSettingComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(systemSettingComponentsPage.table);
    await waitUntilCount(systemSettingComponentsPage.records, beforeRecordsCount + 1);
    expect(await systemSettingComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await systemSettingComponentsPage.deleteSystemSetting();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(systemSettingComponentsPage.records, beforeRecordsCount);
      expect(await systemSettingComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(systemSettingComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
