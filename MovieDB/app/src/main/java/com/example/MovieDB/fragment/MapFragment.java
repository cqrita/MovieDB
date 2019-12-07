package com.example.MovieDB.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.MovieDB.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;


public class MapFragment extends FragmentActivity implements OnMapReadyCallback {
    @BindView(R.id.theater_name)
    TextView theater_name;
    String theaterCode;
    LatLng location;
    String markerTitle;
    String markerId;

    private void setDB(Context ctx) {
        AssetManager assetManager = ctx.getResources().getAssets();
        File dbFile = getDatabasePath("theater.db");
//        File outfile = new File("movie.db");
        InputStream is = null;
        FileOutputStream fo = null;
        long filesize = 0;
        try {
            is = assetManager.open("theater.db", AssetManager.ACCESS_BUFFER);
            OutputStream os = new FileOutputStream(dbFile);
            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0) {
                os.write(buffer);
            }
            os.flush();
            os.close();

            is.close();
        } catch (IOException e) {
            Log.e("DB", e.getLocalizedMessage(), e);
        }
    }

    class ProductDBHelper extends SQLiteOpenHelper {  //새로 생성한 adapter 속성은 SQLiteOpenHelper이다.
        public ProductDBHelper(Context context) {
            super(context, "movie.db", null, 1);    // db명과 버전만 정의 한다.
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }
    }







    GoogleMap mMap;

    public MapFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);

        // SupportMapFragment을 통해 레이아웃에 만든 fragment의 ID를 참조하고 구글맵을 호출한다.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //getMapAsync must be called on the main thread.

    }

    @Override
    //구글맵을 띄울 준비가 되었으면 자동 호출
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //지도타입 - 일반
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //oneMarker();
        manyMarker();
    }

    //마커하나찍는 기본 예제

    /*
    public void oneMarker() {
        // 서울 여의도에 대한 위치 설정
        LatLng seoul = new LatLng(37.52487, 126.92723);


        // 구글 맵에 표시할 마커에 대한 옵션 설정  (알파는 좌표의 투명도이다.)
        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(seoul)
                .title("원하는 위치(위도, 경도)에 마커를 표시했습니다.")
                .snippet("여기는 여의도인거같네여!!")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .alpha(0.5f);

        // 마커를 생성한다. showInfoWindow를 쓰면 처음부터 마커에 상세정보가 뜨게한다. (안쓰면 마커눌러야뜸)
        mMap.addMarker(makerOptions); //.showInfoWindow();

        //정보창 클릭 리스너
        mMap.setOnInfoWindowClickListener(infoWindowClickListener);

        //마커 클릭 리스너
        mMap.setOnMarkerClickListener(markerClickListener);

        //카메라를 여의도 위치로 옮긴다.
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
        //처음 줌 레벨 설정 (해당좌표=>서울, 줌레벨(16)을 매개변수로 넣으면 된다.) (위에 코드대신 사용가능)(중첩되면 이걸 우선시하는듯)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MapFragment.this, "눌렀습니다!!", Toast.LENGTH_LONG);
                return false;
            }
        });



    }
    */
    ////////////////////////  구글맵 마커 여러개생성 및 띄우기 //////////////////////////
    public void manyMarker() {

        setDB(this);
        ProductDBHelper mHelper = new ProductDBHelper(this);
        SQLiteDatabase db = mHelper.getWritableDatabase();


        Cursor cursor = db.rawQuery("SELECT name,lon,lat,theaterCode from skku_db.login where uid = 1;", null); //쿼리문
        cursor.moveToFirst();
        
        if (cursor.moveToFirst()){
            // for loop를 통한 n개의 마커 생성
            for (int idx = 0; idx < 28; idx++) {
                // 1. 마커 옵션 설정 (만드는 과정)
                MarkerOptions makerOptions = new MarkerOptions();

                makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                        .position(new LatLng(cursor.getDouble(2), cursor.getDouble(1)))
                        .snippet(cursor.getString(3))
                        .title(cursor.getString(0)); // 타이틀


                // 2. 마커 생성 (마커를 나타냄)
                mMap.addMarker(makerOptions);
                cursor.moveToNext();
            }
        }
        //정보창 클릭 리스너
        mMap.setOnInfoWindowClickListener(infoWindowClickListener);

        //마커 클릭 리스너
        mMap.setOnMarkerClickListener(markerClickListener);

        // 카메라를 위치로 옮긴다.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.52487, 126.92723)));
    }

    //마커정보창 클릭리스너는 다작동하나, 마커클릭리스너는 snippet정보가 있으면 중복되어 이벤트처리가 안되는거같다.
    // oneMarker(); 는 동작하지않으나 manyMarker(); 는 snippet정보가 없어 동작이가능하다.

    //정보창 클릭 리스너
    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String markerId = marker.getId();
            Toast.makeText(MapFragment.this, "정보창 클릭 Marker ID : "+markerId, Toast.LENGTH_SHORT).show();
            theaterCode = marker.getSnippet();
            mWebView.loadUrl("http://section.cgv.co.kr/theater/timetable/Default.aspx?code="+theaterCode);
        }
    };

    //마커 클릭 리스너
    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            markerId = marker.getId();
            //선택한 타겟위치
            location = marker.getPosition();
            markerTitle = marker.getTitle();
            Toast.makeText(MapFragment.this, "마커 클릭 Marker ID : "+markerId+"("+location.latitude+" "+location.longitude+")", Toast.LENGTH_SHORT).show();
            theaterCode = marker.getSnippet();
            return false;
        }
    };



    //여기서 부터 웹뷰
    private WebView mWebView;
    private WebSettings mWebSettings;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mWebView = (WebView)findViewById(R.id.cgv);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("http://section.cgv.co.kr/theater/timetable/Default.aspx");
    }






    }
