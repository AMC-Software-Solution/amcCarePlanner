import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import DisabilityComponentsPage from './disability.page-object';
import DisabilityUpdatePage from './disability-update.page-object';
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

describe('Disability e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let disabilityComponentsPage: DisabilityComponentsPage;
  let disabilityUpdatePage: DisabilityUpdatePage;

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
    disabilityComponentsPage = new DisabilityComponentsPage();
    disabilityComponentsPage = await disabilityComponentsPage.goToPage(navBarPage);
  });

  it('should load Disabilities', async () => {
    expect(await disabilityComponentsPage.title.getText()).to.match(/Disabilities/);
    expect(await disabilityComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Disabilities', async () => {
    const beforeRecordsCount = (await isVisible(disabilityComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(disabilityComponentsPage.table);
    disabilityUpdatePage = await disabilityComponentsPage.goToCreateDisability();
    await disabilityUpdatePage.enterData();

    expect(await disabilityComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(disabilityComponentsPage.table);
    await waitUntilCount(disabilityComponentsPage.records, beforeRecordsCount + 1);
    expect(await disabilityComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await disabilityComponentsPage.deleteDisability();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(disabilityComponentsPage.records, beforeRecordsCount);
      expect(await disabilityComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(disabilityComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
