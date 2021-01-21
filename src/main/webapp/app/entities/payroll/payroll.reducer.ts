import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPayroll, defaultValue } from 'app/shared/model/payroll.model';

export const ACTION_TYPES = {
  FETCH_PAYROLL_LIST: 'payroll/FETCH_PAYROLL_LIST',
  FETCH_PAYROLL: 'payroll/FETCH_PAYROLL',
  CREATE_PAYROLL: 'payroll/CREATE_PAYROLL',
  UPDATE_PAYROLL: 'payroll/UPDATE_PAYROLL',
  DELETE_PAYROLL: 'payroll/DELETE_PAYROLL',
  RESET: 'payroll/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPayroll>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PayrollState = Readonly<typeof initialState>;

// Reducer

export default (state: PayrollState = initialState, action): PayrollState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PAYROLL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PAYROLL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PAYROLL):
    case REQUEST(ACTION_TYPES.UPDATE_PAYROLL):
    case REQUEST(ACTION_TYPES.DELETE_PAYROLL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PAYROLL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PAYROLL):
    case FAILURE(ACTION_TYPES.CREATE_PAYROLL):
    case FAILURE(ACTION_TYPES.UPDATE_PAYROLL):
    case FAILURE(ACTION_TYPES.DELETE_PAYROLL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYROLL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYROLL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PAYROLL):
    case SUCCESS(ACTION_TYPES.UPDATE_PAYROLL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PAYROLL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/payrolls';

// Actions

export const getEntities: ICrudGetAllAction<IPayroll> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PAYROLL_LIST,
    payload: axios.get<IPayroll>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPayroll> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PAYROLL,
    payload: axios.get<IPayroll>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPayroll> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PAYROLL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPayroll> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PAYROLL,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPayroll> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PAYROLL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
