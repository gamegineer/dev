<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE helpset PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 2.0//EN" "http://java.sun.com/products/javahelp/helpset_2_0.dtd">
<helpset version="2.0">
	<title>Help</title>

	<maps>
		<homeID>main</homeID>
		<mapref location="map.jhm" />
	</maps>

	<view>
		<name>Contents</name>
		<label>Contents</label>
		<type>javax.help.TOCView</type>
		<data>toc.xml</data>
	</view>
	<view>
		<name>Index</name>
		<label>Index</label>
		<type>javax.help.IndexView</type>
		<data>index.xml</data>
	</view>
	<view>
		<name>Search</name>
		<label>Search</label>
		<type>javax.help.SearchView</type>
		<data engine="com.sun.java.help.search.DefaultSearchEngine">JavaHelpSearch</data>
	</view>
	<view>
		<name>Favorites</name>
		<label>Favorites</label>
		<type>javax.help.FavoritesView</type>
	</view>

	<presentation default="true">
		<name>main</name>
		<size width="1024" height="768" />
		<location x="200" y="200" />
		<image>toplevelfolder</image>
		<toolbar>
			<helpaction>javax.help.BackAction</helpaction>
			<helpaction>javax.help.ForwardAction</helpaction>
			<helpaction>javax.help.SeparatorAction</helpaction>
			<helpaction>javax.help.HomeAction</helpaction>
			<helpaction>javax.help.ReloadAction</helpaction>
			<helpaction>javax.help.SeparatorAction</helpaction>
			<helpaction>javax.help.PrintAction</helpaction>
			<helpaction>javax.help.PrintSetupAction</helpaction>
			<helpaction>javax.help.SeparatorAction</helpaction>
			<helpaction>javax.help.FavoritesAction</helpaction>
		</toolbar>
	</presentation>
</helpset>
