import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EqualityComponentsPage from './equality.page-object';
import EqualityUpdatePage from './equality-update.page-object';
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

describe('Equality e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let equalityComponentsPage: EqualityComponentsPage;
  let equalityUpdatePage: EqualityUpdatePage;

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
    equalityComponentsPage = new EqualityComponentsPage();
    equalityComponentsPage = await equalityComponentsPage.goToPage(navBarPage);
  });

  it('should load Equalities', async () => {
    expect(await equalityComponentsPage.title.getText()).to.match(/Equalities/);
    expect(await equalityComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Equalities', async () => {
    const beforeRecordsCount = (await isVisible(equalityComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(equalityComponentsPage.table);
    equalityUpdatePage = await equalityComponentsPage.goToCreateEquality();
    await equalityUpdatePage.enterData();

    expect(await equalityComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(equalityComponentsPage.table);
    await waitUntilCount(equalityComponentsPage.records, beforeRecordsCount + 1);
    expect(await equalityComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await equalityComponentsPage.deleteEquality();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(equalityComponentsPage.records, beforeRecordsCount);
      expect(await equalityComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(equalityComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
