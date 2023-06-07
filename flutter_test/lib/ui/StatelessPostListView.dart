import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:test1/ui/PostViewModel.dart';

class StatelessPostListView extends StatelessWidget {
  const StatelessPostListView({super.key});

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<PostViewModel>(context);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Random Fact About Cats'),
      ),
      body: Column(
        children: [
          ElevatedButton(
            onPressed: () {
              viewModel.fetchPosts();
            },
            style: ElevatedButton.styleFrom(
                textStyle: const TextStyle(fontSize: 60, color: Color(0x2b2b2b)),
                elevation: 0,
                backgroundColor: const Color(0x00000000)),
            child: const Text('🐾'),
          ),
          if (viewModel.isLoading)
            Expanded(
              child: Stack(
                children: [
                  ListView.builder(
                    itemCount: viewModel.posts.length,
                    itemBuilder: (context, index) {
                      final post = viewModel.posts[index];
                      return ListTile(
                        title: Text(post.content),
                      );
                    },
                  ),
                  const Center(
                    child: CircularProgressIndicator(),
                  ),
                ],
              ),
            )
          else
            Expanded(
              child: ListView.builder(
                itemCount: viewModel.posts.length,
                itemBuilder: (context, index) {
                  final post = viewModel.posts[index];
                  return ListTile(
                    title: Text(post.content),
                  );
                },
              ),
            ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          viewModel.clearPost();
        },
        child: const Icon(Icons.delete),
      ),
    );
  }
}
