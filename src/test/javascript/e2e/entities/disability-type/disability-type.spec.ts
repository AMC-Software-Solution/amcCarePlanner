import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import DisabilityTypeComponentsPage from './disability-type.page-object';
import DisabilityTypeUpdatePage from './disability-type-update.page-object';
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

describe('DisabilityType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let disabilityTypeComponentsPage: DisabilityTypeComponentsPage;
  let disabilityTypeUpdatePage: DisabilityTypeUpdatePage;

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
    disabilityTypeComponentsPage = new DisabilityTypeComponentsPage();
    disabilityTypeComponentsPage = await disabilityTypeComponentsPage.goToPage(navBarPage);
  });

  it('should load DisabilityTypes', async () => {
    expect(await disabilityTypeComponentsPage.title.getText()).to.match(/Disability Types/);
    expect(await disabilityTypeComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete DisabilityTypes', async () => {
    const beforeRecordsCount = (await isVisible(disabilityTypeComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(disabilityTypeComponentsPage.table);
    disabilityTypeUpdatePage = await disabilityTypeComponentsPage.goToCreateDisabilityType();
    await disabilityTypeUpdatePage.enterData();

    expect(await disabilityTypeComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(disabilityTypeComponentsPage.table);
    await waitUntilCount(disabilityTypeComponentsPage.records, beforeRecordsCount + 1);
    expect(await disabilityTypeComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await disabilityTypeComponentsPage.deleteDisabilityType();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(disabilityTypeComponentsPage.records, beforeRecordsCount);
      expect(await disabilityTypeComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(disabilityTypeComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
