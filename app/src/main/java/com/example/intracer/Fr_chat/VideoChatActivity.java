package com.example.intracer.Fr_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.intracer.Fr_groups.CreateActivity;
import com.example.intracer.Fr_groups.GroupActivity;
import com.example.intracer.Fr_groups.ScanQRActivity;
import com.example.intracer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class VideoChatActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 124;
    private Session session;
    private FrameLayout publisherViewContainer;
    private FrameLayout subscriberViewContainer;
    private Publisher publisher;
    private Subscriber subscriber;
    private FloatingActionButton EndCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat);
        requestPermissions();

        publisherViewContainer = findViewById(R.id.publisher_container);
        subscriberViewContainer = findViewById(R.id.subscriber_container);

        EndCall= findViewById(R.id.CallEnd);
        EndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.disconnect();
                Intent intent = new Intent(VideoChatActivity.this, ChatActivity.class );
                startActivity(intent);
                finish();
            }
        });
    }

    private Session.SessionListener sessionListener = new Session.SessionListener() {
        @Override
        public void onConnected(Session session) {
            System.out.println("onConnected: Connected to session: " + session.getSessionId());
            publisher = new Publisher.Builder(VideoChatActivity.this).build();
            publisher.setPublisherListener(publisherListener);
            publisher.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);

            publisherViewContainer.addView(publisher.getView());

            if (publisher.getView() instanceof GLSurfaceView) {
                ((GLSurfaceView) publisher.getView()).setZOrderOnTop(true);
            }

            session.publish(publisher);
        }

        @Override
        public void onDisconnected(Session session) {
            System.out.println("onDisconnected: Disconnected from session: " + session.getSessionId());
            Intent intent = new Intent(VideoChatActivity.this, ChatActivity.class );
            startActivity(intent);
            finish();
        }

        @Override
        public void onStreamReceived(Session session, Stream stream) {
            System.out.println( "onStreamReceived: New Stream Received " + stream.getStreamId() + " in session: " + session.getSessionId());

            if (subscriber == null) {
                subscriber = new Subscriber.Builder(VideoChatActivity.this, stream).build();
                subscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
                subscriber.setSubscriberListener(subscriberListener);
                session.subscribe(subscriber);
                subscriberViewContainer.addView(subscriber.getView());
            }
        }

        @Override
        public void onStreamDropped(Session session, Stream stream) {
            System.out.println("Stream Dropped");

            if (subscriber != null) {
                subscriber = null;
                subscriberViewContainer.removeAllViews();
            }
            session.disconnect();
            Intent intent = new Intent(VideoChatActivity.this, ChatActivity.class );
            startActivity(intent);
            finish();
        }

        @Override
        public void onError(Session session, OpentokError opentokError) {
            System.out.println("Session error: " + opentokError.getMessage());

        }
    };

    private PublisherKit.PublisherListener publisherListener = new PublisherKit.PublisherListener() {
        @Override
        public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
            System.out.println("onStreamCreated: Publisher Stream Created. Own stream " + stream.getStreamId());
        }

        @Override
        public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
            System.out.println("onStreamDestroyed: Publisher Stream Destroyed. Own stream " + stream.getStreamId());
        }

        @Override
        public void onError(PublisherKit publisherKit, OpentokError opentokError) {
            System.out.println("PublisherKit onError: " + opentokError.getMessage());
        }
    };

    SubscriberKit.SubscriberListener subscriberListener = new SubscriberKit.SubscriberListener() {
        @Override
        public void onConnected(SubscriberKit subscriberKit) {
            System.out.println("onConnected: Subscriber connected. Stream: " + subscriberKit.getStream().getStreamId());
        }

        @Override
        public void onDisconnected(SubscriberKit subscriberKit) {
            System.out.println("onDisconnected: Subscriber disconnected. Stream: " + subscriberKit.getStream().getStreamId());
        }

        @Override
        public void onError(SubscriberKit subscriberKit, OpentokError opentokError) {
            System.out.println("SubscriberKit onError: " + opentokError.getMessage());
        }

    };


    @Override
    protected void onPause() {
        super.onPause();

        if (session != null) {
            session.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (session != null) {
            session.onResume();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(PERMISSIONS_REQUEST_CODE)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize and connect to the session
            initializeSession(OpenTokConfig.API_KEY, OpenTokConfig.SESSION_ID, OpenTokConfig.TOKEN);


        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", PERMISSIONS_REQUEST_CODE, perms);
        }
    }
    private void initializeSession(String apiKey, String sessionId, String token) {
        System.out.println("apiKey: " + apiKey+
            "sessionId: " + sessionId+
            "token: " + token
        );


        session = new Session.Builder(this, apiKey, sessionId).build();
        session.setSessionListener(sessionListener);
        session.connect(token);
    }
}