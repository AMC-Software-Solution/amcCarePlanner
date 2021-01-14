import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './terminal-device.reducer';
import { ITerminalDevice } from 'app/shared/model/terminal-device.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ITerminalDeviceProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const TerminalDevice = (props: ITerminalDeviceProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { terminalDeviceList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="terminal-device-heading">
        <Translate contentKey="carePlannerApp.terminalDevice.home.title">Terminal Devices</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.terminalDevice.home.createLabel">Create new Terminal Device</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {terminalDeviceList && terminalDeviceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('deviceName')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.deviceName">Device Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('deviceModel')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.deviceModel">Device Model</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('registeredDate')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.registeredDate">Registered Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imei')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.imei">Imei</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('simNumber')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.simNumber">Sim Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('userStartedUsingFrom')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.userStartedUsingFrom">User Started Using From</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('deviceOnLocationFrom')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.deviceOnLocationFrom">Device On Location From</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('operatingSystem')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.operatingSystem">Operating System</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ownerEntityId')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.ownerEntityId">Owner Entity Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ownerEntityName')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.ownerEntityName">Owner Entity Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientId')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.clientId">Client Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hasExtraData')}>
                  <Translate contentKey="carePlannerApp.terminalDevice.hasExtraData">Has Extra Data</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.terminalDevice.employee">Employee</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {terminalDeviceList.map((terminalDevice, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${terminalDevice.id}`} color="link" size="sm">
                      {terminalDevice.id}
                    </Button>
                  </td>
                  <td>{terminalDevice.deviceName}</td>
                  <td>{terminalDevice.deviceModel}</td>
                  <td>
                    {terminalDevice.registeredDate ? (
                      <TextFormat type="date" value={terminalDevice.registeredDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{terminalDevice.imei}</td>
                  <td>{terminalDevice.simNumber}</td>
                  <td>
                    {terminalDevice.userStartedUsingFrom ? (
                      <TextFormat type="date" value={terminalDevice.userStartedUsingFrom} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {terminalDevice.deviceOnLocationFrom ? (
                      <TextFormat type="date" value={terminalDevice.deviceOnLocationFrom} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{terminalDevice.operatingSystem}</td>
                  <td>{terminalDevice.note}</td>
                  <td>{terminalDevice.ownerEntityId}</td>
                  <td>{terminalDevice.ownerEntityName}</td>
                  <td>
                    {terminalDevice.createdDate ? (
                      <TextFormat type="date" value={terminalDevice.createdDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {terminalDevice.lastUpdatedDate ? (
                      <TextFormat type="date" value={terminalDevice.lastUpdatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{terminalDevice.clientId}</td>
                  <td>{terminalDevice.hasExtraData ? 'true' : 'false'}</td>
                  <td>
                    {terminalDevice.employeeEmployeeCode ? (
                      <Link to={`employee/${terminalDevice.employeeId}`}>{terminalDevice.employeeEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${terminalDevice.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${terminalDevice.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${terminalDevice.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="carePlannerApp.terminalDevice.home.notFound">No Terminal Devices found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={terminalDeviceList && terminalDeviceList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ terminalDevice }: IRootState) => ({
  terminalDeviceList: terminalDevice.entities,
  loading: terminalDevice.loading,
  totalItems: terminalDevice.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TerminalDevice);
