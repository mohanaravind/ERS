/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */

package com.mohanaravind.main;

public final class R {
    public static final class attr {
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static final int buttonBarButtonStyle=0x7f010001;
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static final int buttonBarStyle=0x7f010000;
    }
    public static final class color {
        public static final int black_overlay=0x7f050000;
        public static final int gray_light=0x7f050002;
        public static final int white_title=0x7f050001;
    }
    public static final class drawable {
        public static final int addcontact=0x7f020000;
        public static final int app_title=0x7f020001;
        public static final int backgroundtransparency=0x7f020002;
        public static final int emergency=0x7f020003;
        public static final int frame_background=0x7f020004;
        public static final int ic_launcher=0x7f020005;
        public static final int mya=0x7f020006;
        public static final int red_button=0x7f020007;
        public static final int simplepage_background=0x7f020008;
        public static final int sos_button=0x7f020009;
        public static final int welcome_background=0x7f02000a;
    }
    public static final class id {
        public static final int btnOk=0x7f090005;
        public static final int button1=0x7f090006;
        public static final int imageView1=0x7f090008;
        public static final int imageView2=0x7f090007;
        public static final int menu_settings=0x7f09000a;
        public static final int pager=0x7f090000;
        public static final int sos_button=0x7f090009;
        public static final int textView1=0x7f090001;
        public static final int textView3=0x7f090004;
        public static final int txtPhoneNumber=0x7f090003;
        public static final int txtVerifyDescription=0x7f090002;
    }
    public static final class layout {
        public static final int activity_configure=0x7f030000;
        public static final int activity_verify=0x7f030001;
        public static final int activity_welcome=0x7f030002;
        public static final int widget_sos=0x7f030003;
    }
    public static final class menu {
        public static final int activity_configure=0x7f080000;
    }
    public static final class string {
        public static final int apiKey=0x7f06000f;
        public static final int app_name=0x7f060000;
        public static final int button_continue=0x7f060002;
        public static final int json_name_passphrase=0x7f060011;
        public static final int json_name_result=0x7f060014;
        public static final int json_name_seed=0x7f060013;
        public static final int json_name_token=0x7f060012;
        public static final int menu_settings=0x7f060001;
        public static final int ok_verify=0x7f06000c;
        public static final int result_success_code=0x7f060015;
        public static final int service_mailid=0x7f060010;
        public static final int sos_red=0x7f060003;
        public static final int title_activity=0x7f060004;
        public static final int title_section1=0x7f060006;
        public static final int title_section2=0x7f060007;
        public static final int title_section3=0x7f060008;
        public static final int title_verify=0x7f060009;
        public static final int title_welcome=0x7f060005;
        public static final int url_regiseruser=0x7f06000e;
        public static final int verify_description=0x7f06000a;
        public static final int verify_instruction=0x7f06000b;
        public static final int verify_message=0x7f06000d;
    }
    public static final class style {
        /** 
        Base application theme, dependent on API level. This theme is replaced
        by AppBaseTheme from res/values-vXX/styles.xml on newer devices.
    

            Theme customizations available in newer API levels can go in
            res/values-vXX/styles.xml, while customizations related to
            backward-compatibility can go here.
        

        Base application theme for API 11+. This theme completely replaces
        AppBaseTheme from res/values/styles.xml on API 11+ devices.
    
 API 11 theme customizations can go here. 

        Base application theme for API 14+. This theme completely replaces
        AppBaseTheme from BOTH res/values/styles.xml and
        res/values-v11/styles.xml on API 14+ devices.
    
 API 14 theme customizations can go here. 
         */
        public static final int AppBaseTheme=0x7f070000;
        /**  Application theme. 
 All customizations that are NOT specific to a particular API-level can go here. 
         */
        public static final int AppTheme=0x7f070001;
        public static final int ButtonBar=0x7f070003;
        public static final int ButtonBarButton=0x7f070002;
        public static final int FullscreenTheme=0x7f070004;
        /**  
    android:id="@+id/textView1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:layout_alignParentTop="true"
        android:layout_marginLeft="18dp"
        android:layout_marginTop="14dp"
        android:text="@string/title_verify"
        android:textAppearance="?android:attr/textAppearanceMedium"
        android:textColor="@color/white_title"
        android:textStyle="bold"
        android:typeface="serif" 
         */
        public static final int TitleBar=0x7f070005;
    }
    public static final class xml {
        public static final int widgetproviderinfo=0x7f040000;
    }
    public static final class styleable {
        /** 
         Declare custom theme attributes that allow changing which styles are
         used for button bars depending on the API level.
         ?android:attr/buttonBarStyle is new as of API 11 so this is
         necessary to support previous API levels.
    
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #ButtonBarContainerTheme_buttonBarButtonStyle com.mohanaravind.main:buttonBarButtonStyle}</code></td><td></td></tr>
           <tr><td><code>{@link #ButtonBarContainerTheme_buttonBarStyle com.mohanaravind.main:buttonBarStyle}</code></td><td></td></tr>
           </table>
           @see #ButtonBarContainerTheme_buttonBarButtonStyle
           @see #ButtonBarContainerTheme_buttonBarStyle
         */
        public static final int[] ButtonBarContainerTheme = {
            0x7f010000, 0x7f010001
        };
        /**
          <p>This symbol is the offset where the {@link com.mohanaravind.main.R.attr#buttonBarButtonStyle}
          attribute's value can be found in the {@link #ButtonBarContainerTheme} array.


          <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
          @attr name android:buttonBarButtonStyle
        */
        public static final int ButtonBarContainerTheme_buttonBarButtonStyle = 1;
        /**
          <p>This symbol is the offset where the {@link com.mohanaravind.main.R.attr#buttonBarStyle}
          attribute's value can be found in the {@link #ButtonBarContainerTheme} array.


          <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
          @attr name android:buttonBarStyle
        */
        public static final int ButtonBarContainerTheme_buttonBarStyle = 0;
    };
}
